package com.c10.finalproject.ui.admin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeAdminViewModel @Inject constructor(private val ticketRepository: TicketRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel

    private val _homeAdminTicket = MutableLiveData<Resource<List<Data>>>()
    val homeAdminTicket: LiveData<Resource<List<Data>>> get() = _homeAdminTicket

    init {
        getHomeAdminTicket()
    }

    fun getHomeAdminTicket() = viewModelScope.launch(Dispatchers.IO) {
        _homeAdminTicket.postValue(Resource.Loading())
        val ticket = ticketRepository.getTickets()
        val ticketList = mutableListOf<Data>()

        ticket.payload?.forEach {
//            ticketList.clear()
            ticketList.add(it)
        }

        viewModelScope.launch(Dispatchers.Main) {
            _homeAdminTicket.postValue(Resource.Success(ticketList))

        }
    }

}