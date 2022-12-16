package com.c10.finalproject.ui.user.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    fun clearToken() = viewModelScope.launch {
        dataStoreManager.clearToken()
    }
}
