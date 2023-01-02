package com.c10.finalproject.ui.admin.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.data.remote.tickets.model.histories.DataHistories
import com.c10.finalproject.data.remote.tickets.model.histories.DataUsers
import com.c10.finalproject.data.repository.HistoriesRepository
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
class TransactionAdminViewModel @Inject constructor(
    private val historiesRepository: HistoriesRepository,
    private val ticketRepository: TicketRepository
) :
    ViewModel() {
    // TODO: Implement the ViewModel

    private val _historiesTicket = MutableLiveData<Resource<List<DataHistories>>>()
    val historiesTicket: LiveData<Resource<List<DataHistories>>> get() = _historiesTicket

    private val _ticketResult = MutableLiveData<Resource<List<Data>>>()
    val ticketResult: LiveData<Resource<List<Data>>> get() = _ticketResult

    private val _userResult = MutableLiveData<Resource<List<DataUsers>>>()
    val userResult: LiveData<Resource<List<DataUsers>>> get() = _userResult

    private val _historiesId = MutableLiveData<Resource<List<String>>>()
    val historiesId: LiveData<Resource<List<String>>> get() = _historiesId


    fun getTransactionHistories() = viewModelScope.launch(Dispatchers.IO) {
        _historiesId.postValue(Resource.Loading())
        val historiesId = historiesRepository.getHistories()
        val historiesIdList = mutableListOf<String>()

        historiesId.payload?.forEach {
            historiesIdList.add(it.ticketId.toString())
        }

        Log.d("LISTTT ID HISTORY111", historiesIdList.toString())


        _ticketResult.postValue(Resource.Loading())
        val ticket = ticketRepository.getTickets()
        val ticketList = mutableListOf<Data>()

        ticket.payload?.forEach {

            for (i in historiesIdList.indices) {
                if (it.id?.equals(historiesIdList[i].toInt()) == true){
                    ticketList.add(it)
                }
            }

        }

        Log.d("LISTTT TICKET", ticketList.toString())

        viewModelScope.launch(Dispatchers.Main) {
            _ticketResult.postValue(Resource.Success(ticketList.distinct()))
            _historiesId.postValue(Resource.Success(historiesIdList.distinct()))

        }

    }

}