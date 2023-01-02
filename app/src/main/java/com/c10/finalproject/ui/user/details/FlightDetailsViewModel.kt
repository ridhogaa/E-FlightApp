package com.c10.finalproject.ui.user.details

import androidx.lifecycle.*
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.data.repository.WishlistRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val wishlistRepository: WishlistRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _detailResult = MutableLiveData<Resource<GetTicketByIdResponse>>()
    val detailResult: LiveData<Resource<GetTicketByIdResponse>> get() = _detailResult

    private val _isFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _isFav

    fun isFavorite(ticketId: Int, userId: Int) = CoroutineScope(Dispatchers.IO).launch {
        _isFav.postValue(
            wishlistRepository.isFavorite(ticketId, userId)
        )
    }

    fun addFavorite(wishlistEntity: WishlistEntity) = CoroutineScope(Dispatchers.IO).launch {
        wishlistRepository.addFavorite(wishlistEntity)
    }

    fun deleteFavorite(wishlistEntity: WishlistEntity) = CoroutineScope(Dispatchers.IO).launch {
        wishlistRepository.deleteFavorite(wishlistEntity.ticketId!!, wishlistEntity.userId!!)
    }

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailResult.postValue(Resource.Loading())
            try {
                val data = ticketRepository.getTicketById(id)
                if (data.payload != null) {
                    viewModelScope.launch(Dispatchers.Main) {
                        _detailResult.postValue(Resource.Success(data.payload))
                    }
                } else {
                    _detailResult.postValue(Resource.Error(data.exception, null))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _detailResult.postValue(Resource.Error(e, null))
                }
            }
        }
    }

    fun getToken() = dataStoreManager.getToken.asLiveData()

    fun getId() = dataStoreManager.getId.asLiveData()
}