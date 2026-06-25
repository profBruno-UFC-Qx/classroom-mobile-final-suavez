package com.example.projectstudy.data.local.repository

import android.content.Context
import androidx.core.content.edit
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.remote.api.AuthApi
import com.example.projectstudy.data.remote.dto.RegisterRequestDto
import com.example.projectstudy.data.remote.dto.UserDto
import com.example.projectstudy.data.remote.mapper.toEntity
import com.example.projectstudy.data.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.projectstudy.data.sync.RemoteSyncService

@Singleton
class LocalAuthRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val remoteSyncService: RemoteSyncService
) : AuthRepository {

    private val preferences = context.getSharedPreferences(
        "auth_session",
        Context.MODE_PRIVATE
    )

    private val _isLoggedIn = MutableStateFlow(
        hasValidLocalSession()
    )

    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit> {
        return runCatching {
            val response = authApi.login(
                username = username,
                password = password
            )

            saveSession(
                accessToken = response.accessToken,
                user = response.user
            )
        }
    }

    override suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        institution: String,
        course: String
    ): Result<Unit> {
        return runCatching {
            val response = authApi.register(
                RegisterRequestDto(
                    name = name,
                    username = username,
                    email = email,
                    password = password,
                    institution = institution,
                    course = course
                )
            )

            saveSession(
                accessToken = response.accessToken,
                user = response.user
            )
        }
    }

    override suspend fun getAccessToken(): String? {
        return preferences.getString(KEY_ACCESS_TOKEN, null)
    }

    override suspend fun getCurrentUserId(): String? {
        return preferences.getString(KEY_CURRENT_USER_ID, null)
    }

    override suspend fun setLoggedIn(value: Boolean) {
        if (!value) {
            logout()
            return
        }

        val hasSession = hasValidLocalSession()

        preferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, hasSession)
        }

        _isLoggedIn.value = hasSession
    }

    override suspend fun logout() {
        preferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, false)
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_CURRENT_USER_ID)
        }

        _isLoggedIn.value = false
    }

    private suspend fun saveSession(
        accessToken: String,
        user: UserDto
    ) {
        userDao.upsertUser(user.toEntity())

        preferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_CURRENT_USER_ID, user.id)
        }

        runCatching {
            remoteSyncService.pullAndSave(
                accessToken = accessToken
            )
        }

        _isLoggedIn.value = true
    }

    private fun hasValidLocalSession(): Boolean {
        val isLoggedIn = preferences.getBoolean(
            KEY_IS_LOGGED_IN,
            false
        )

        val accessToken = preferences.getString(
            KEY_ACCESS_TOKEN,
            null
        )

        val currentUserId = preferences.getString(
            KEY_CURRENT_USER_ID,
            null
        )

        return isLoggedIn &&
                !accessToken.isNullOrBlank() &&
                !currentUserId.isNullOrBlank()
    }

    private companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_CURRENT_USER_ID = "current_user_id"
    }
}