package com.c10.finalproject.ui.user.history

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.order.DataOrder
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.data.repository.OrderRepository
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val orderRepository: OrderRepository,
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _history = MutableLiveData<Resource<List<DataOrder>>>()
    val history: LiveData<Resource<List<DataOrder>>> get() = _history

    private val _ticket = MutableLiveData<List<DataTicket>>()
    val ticket: LiveData<List<DataTicket>> get() = _ticket

    fun getOrder(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _history.postValue(Resource.Loading())
        try {
            val order = orderRepository.getOrders()
            val orderList = mutableListOf<DataOrder>()
            val ticketList = mutableListOf<DataTicket>()
            if (order.payload != null) {
                order.payload.forEach {
                    if (it.userId.toString().equals(userId.toString(), false)) {
                        orderList.add(it)
                        ticketList.add(ticketRepository.getTicketById(it.ticketId!!).payload!!.data!!)
                    } else {
                        orderList.clear()
                        ticketList.clear()
                    }
                }
                if (orderList.size > 0) {
                    viewModelScope.launch(Dispatchers.Main) {
                        _history.postValue(Resource.Success(orderList))
                        _ticket.postValue(ticketList)
                    }
                } else {
                    _history.postValue(Resource.Empty())
                }
            } else {
                _history.postValue(Resource.Error(order.exception, null))
            }
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _history.postValue(Resource.Error(e, null))
            }
        }

    }

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()

    fun getId() = dataStoreManager.getId.asLiveData()
}