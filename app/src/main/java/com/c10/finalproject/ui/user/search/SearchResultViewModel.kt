package com.c10.finalproject.ui.user.search

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
class SearchResultViewModel @Inject constructor(private val ticketRepository: TicketRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel

    private val _searchResult = MutableLiveData<Resource<List<Data>>>()
    val searchResult: LiveData<Resource<List<Data>>> get() = _searchResult

    fun getSearchResult(
        origin: String,
        destination: String,
        category: String,
        departureDate: String,
        returnDate: String?
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            _searchResult.postValue(Resource.Loading())
            val ticket = ticketRepository.getTicket()
            val ticketList = mutableListOf<Data>()
            ticket.payload?.forEach {
                if (it.origin.equals(origin)
                    && it.destination.equals(destination)
                    && it.category.equals(category)
                    && it.departureTime?.substring(0, 10).equals(departureDate)
                    && it.returnTime?.substring(0, 10).equals(returnDate)
                ) {
                    ticketList.clear()
                    ticketList.add(it)
                } else {
                    _searchResult.postValue(Resource.Empty())
                }

            }
            if (ticketList.size > 0) {
                viewModelScope.launch(Dispatchers.Main) {
                    _searchResult.postValue(Resource.Success(ticketList))
                }
            }
        }
}