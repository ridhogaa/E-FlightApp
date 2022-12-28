package com.c10.finalproject.ui.user.details

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.data.repository.WishlistRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val wishlistRepository: WishlistRepository,
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _detailResult = MutableLiveData<Resource<GetTicketByIdResponse>>()
    val detailResult: LiveData<Resource<GetTicketByIdResponse>> get() = _detailResult

    private val _isFav: MutableLiveData<Boolean?> = MutableLiveData()
    val isFav: LiveData<Boolean?> get() = _isFav

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailResult.postValue(Resource.Loading())
            val data = ticketRepository.getTicketById(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailResult.postValue(Resource.Success(data.payload!!))
            }
        }
    }

    fun addWishlist(token: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        wishlistRepository.addWishlist(token, id)
    }

    fun deleteWishlist(token: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val data = wishlistRepository.getWishlists()
        val user = userRepository.getUserByToken(token).payload
        data.payload?.forEach {
            if (it.ticketId == id && it.userId == user?.data?.id!!) {
                wishlistRepository.deleteWishlist(token, it.id!!)
            }
        }
    }

    fun getWishlist(token: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val data = wishlistRepository.getWishlists()
        val user = userRepository.getUserByToken(token)
        data.payload?.forEach {
            _isFav.postValue(it.ticketId == id && it.userId == user.payload?.data?.id!!)
        }
    }

    fun getToken() = dataStoreManager.getToken.asLiveData()
}