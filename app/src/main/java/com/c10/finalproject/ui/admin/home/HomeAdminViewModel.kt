package com.c10.finalproject.ui.admin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeAdminViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val dataStoreManager: DataStoreManager,
) :
    ViewModel() {
    // TODO: Implement the ViewModel

    private val _homeAdminTicket = MutableLiveData<Resource<List<Data>>>()
    val homeAdminTicket: LiveData<Resource<List<Data>>> get() = _homeAdminTicket

    fun getHomeAdminTicket() = viewModelScope.launch(Dispatchers.IO) {
        _homeAdminTicket.postValue(Resource.Loading())

        try {
            val ticket = ticketRepository.getTickets()

            if (ticket.payload != null) {
                viewModelScope.launch(Dispatchers.Main) {

                    val ticketShowed = ticketRepository.getTickets().payload
                    _homeAdminTicket.postValue(Resource.Success(ticketShowed!!.sortedByDescending { it.id }))
                }
            } else {
                _homeAdminTicket.postValue(Resource.Error(ticket.exception, null))
            }
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _homeAdminTicket.postValue(Resource.Error(e, null))
            }
        }

    }

    fun clear() = CoroutineScope(Dispatchers.IO).launch {
        dataStoreManager.clear()
    }


}