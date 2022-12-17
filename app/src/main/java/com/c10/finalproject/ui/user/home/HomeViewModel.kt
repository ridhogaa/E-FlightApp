package com.c10.finalproject.ui.user.home

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.data.remote.user.model.GetUserResponse
import com.c10.finalproject.data.repository.AuthRepository
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRespository
import com.c10.finalproject.wrapper.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRespository,
    private val dataStoreManager: DataStoreManager,
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _user = MutableLiveData<Result<GetUserResponse>>()
    val user: LiveData<Result<GetUserResponse>> get() = _user

    private val _ticket = MutableLiveData<Resource<List<String>>>()
    val ticket: LiveData<Resource<List<String>>> get() = _ticket

    init {
        getUserByToken()
        getTickets()
    }

    private fun getUserByToken() = viewModelScope.launch {
        dataStoreManager.getToken.collect() { token ->
            userRepository.getUserByToken(token).collect() {
                _user.postValue(it)
            }
        }
    }

    private fun getTickets() = viewModelScope.launch(Dispatchers.IO) {
        _ticket.postValue(Resource.Loading())
        val ticket = ticketRepository.getTickets()
        val ticketList = mutableListOf<String>()
        ticket.payload?.forEach {
            ticketList.add(it.origin.toString())
        }
        viewModelScope.launch(Dispatchers.Main) {
            _ticket.postValue(Resource.Success(ticketList.distinct()))
        }
    }

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()
}