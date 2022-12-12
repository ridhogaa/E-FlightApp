package com.c10.finalproject.ui.user.home

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.GetUserResponse
import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.c10.finalproject.data.repository.AuthRepository
import com.c10.finalproject.data.repository.UserRespository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRespository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableLiveData<Result<GetUserResponse>>()
    val user: LiveData<Result<GetUserResponse>> get() = _user

    init {
        getUserByToken()
    }

    private fun getUserByToken() = viewModelScope.launch {
        dataStoreManager.getToken.collect() { token ->
            userRepository.getUserByToken(token).collect() {
                _user.postValue(it)
            }
        }
    }

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()
}