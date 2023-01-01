package com.c10.finalproject.ui.admin.transaction.detailtransaction

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
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
class DetailTransactionViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    // TODO: Implement the ViewModel
    private val _detailResult = MutableLiveData<Resource<GetTicketByIdResponse>>()
    val detailResult: LiveData<Resource<GetTicketByIdResponse>> get() = _detailResult

    private val _ticket = MutableLiveData<Resource<List<String>>>()
    val ticket: LiveData<Resource<List<String>>> get() = _ticket


    fun getToken() = dataStoreManager.getToken.asLiveData()

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = ticketRepository.getTicketById(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailResult.postValue(data)
            }
        }
    }
}