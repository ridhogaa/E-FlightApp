package com.c10.finalproject.ui.user.home

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager,
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    private val _ticket = MutableLiveData<Resource<List<String>>>()
    val ticket: LiveData<Resource<List<String>>> get() = _ticket

    init {
        getTickets()
    }

    fun getUserByToken(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(Resource.Loading())
        val data = userRepository.getUserByToken(token)
        viewModelScope.launch(Dispatchers.Main) {
            _user.postValue(Resource.Success(data.payload!!))
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