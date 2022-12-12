package com.c10.finalproject.ui.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.GetUserResponse
import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.c10.finalproject.data.repository.AuthRepository
import com.c10.finalproject.data.repository.UserRespository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRespository: UserRespository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    suspend fun getUserByToken(token: String) = userRespository.getUserByToken(token)

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()
}