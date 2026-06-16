package com.example.projectstudy.data.local.repository

import android.content.Context
import com.example.projectstudy.data.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class LocalAuthRepository @Inject constructor(
    @ApplicationContext context: Context
) : AuthRepository {

    private val preferences = context.getSharedPreferences(
        "auth_session",
        Context.MODE_PRIVATE
    )

    private val _isLoggedIn = MutableStateFlow(
        preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    )

    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    override suspend fun setLoggedIn(value: Boolean) {
        preferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, value)
            .apply()

        _isLoggedIn.value = value
    }

    override suspend fun logout() {
        preferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()

        _isLoggedIn.value = false
    }

    private companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
}