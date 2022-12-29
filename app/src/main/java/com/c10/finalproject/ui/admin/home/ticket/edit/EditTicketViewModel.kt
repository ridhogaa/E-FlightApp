package com.c10.finalproject.ui.admin.home.ticket.edit

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.tickets.model.GetTicketByIdResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketBody
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    // TODO: Implement the ViewModel
    private val _detailResult = MutableLiveData<Resource<GetTicketByIdResponse>>()
    val detailResult: LiveData<Resource<GetTicketByIdResponse>> get() = _detailResult

    private val _ticket = MutableLiveData<Resource<List<String>>>()
    val ticket: LiveData<Resource<List<String>>> get() = _ticket

    init {
        getTickets()
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

    fun getToken() = dataStoreManager.getToken.asLiveData()

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = ticketRepository.getTicketById(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailResult.postValue(data)
            }
        }
    }

    suspend fun updateTicket(token: String, id: Int, updateTicketBody: UpdateTicketBody) =
        ticketRepository.updateTicket(token, id, updateTicketBody)

    suspend fun deleteTicket(token: String, id: Int) =
        ticketRepository.deleteTicket(token, id)

}