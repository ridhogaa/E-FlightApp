package com.c10.finalproject.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.auth.model.LoginBody
import com.c10.finalproject.data.remote.auth.model.LoginResponse
import com.c10.finalproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    // TODO: Implement the ViewModel
    suspend fun login(loginBody: LoginBody) = authRepository.login(loginBody)

    fun setToken(token: String) = CoroutineScope(Dispatchers.IO).launch {
        dataStoreManager.setToken(token)
    }
}