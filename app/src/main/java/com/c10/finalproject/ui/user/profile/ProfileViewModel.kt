package com.c10.finalproject.ui.user.profile

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String> get() = _photo

    fun getUserByToken(token: String) = viewModelScope.launch(Dispatchers.Main) {
        _user.postValue(Resource.Loading())
        val data = userRepository.getUserByToken(token)
        _user.postValue(Resource.Success(data.payload!!))
    }

    fun addPhoto(image: String) = _photo.postValue(image)

    fun updateUser(id: Int, bodyUpdateUser: BodyUpdateUser) =
        viewModelScope.launch(Dispatchers.Main) {
            userRepository.updateUserById(id, bodyUpdateUser)
        }


    fun clear() = CoroutineScope(Dispatchers.IO).launch {
        dataStoreManager.clear()
    }

    fun getToken() = dataStoreManager.getToken

}
