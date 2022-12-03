package com.c10.finalproject.ui.auth.register

import androidx.lifecycle.ViewModel
import com.c10.finalproject.data.remote.auth.model.RegisterBody
import com.c10.finalproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :
    ViewModel() {
    // TODO: Implement the ViewModel
    suspend fun register(registerBody: RegisterBody) = authRepository.register(registerBody)
}