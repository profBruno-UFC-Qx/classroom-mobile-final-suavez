package com.example.projectstudy.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectstudy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthSessionViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    val isLoggedIn = authRepository.isLoggedIn
}