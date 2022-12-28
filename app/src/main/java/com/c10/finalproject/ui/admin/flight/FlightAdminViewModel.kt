package com.c10.finalproject.ui.admin.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@HiltViewModel
class FlightAdminViewModel @Inject constructor(private val ticketRepository: TicketRepository) :
    ViewModel() {

    private val _ticket = MutableLiveData<Resource<List<String>>>()
    val ticket: LiveData<Resource<List<String>>> get() = _ticket

    init {
        getTickets()
    }

    fun addTicket(token: String, id: Int) =
        viewModelScope.launch(Dispatchers.IO) { orderRepository.addOrder(token, id) }

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
}