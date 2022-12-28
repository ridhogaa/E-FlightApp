package com.c10.finalproject.ui.user.notification

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.notification.DataNotification
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.data.repository.NotificationRepository
import com.c10.finalproject.data.repository.OrderRepository
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val ticketRepository: TicketRepository,
    private val orderRepository: OrderRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    // TODO: Implement the ViewModel

    private val _notification = MutableLiveData<Resource<List<DataNotification>>>()
    val notification: LiveData<Resource<List<DataNotification>>> get() = _notification

    private val _ticket = MutableLiveData<List<DataTicket>>()
    val ticket: LiveData<List<DataTicket>> get() = _ticket

    fun getNotifications(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _notification.postValue(Resource.Loading())
        val notif = notificationRepository.getNotifications()
        val notifList = mutableListOf<DataNotification>()
        val ticketList = mutableListOf<DataTicket>()
        val order = orderRepository.getOrders().payload
        notif.payload?.forEachIndexed { index, it ->
            if (it.userId == userId && it.orderId == order!![index].id) {
                notifList.add(it)
                ticketList.add(ticketRepository.getTicketById(order[index].ticketId!!).payload!!.data!!)
            } else {
                notifList.clear()
                ticketList.clear()
            }
        }
        if (notifList.size > 0) {
            viewModelScope.launch(Dispatchers.Main) {
                _notification.postValue(Resource.Success(notifList))
                _ticket.postValue(ticketList)
            }
        } else {
            _notification.postValue(Resource.Empty())
        }
    }

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()

    fun getId() = dataStoreManager.getId.asLiveData()

}