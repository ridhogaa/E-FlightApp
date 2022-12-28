package com.c10.finalproject.ui.user.wishlist

import androidx.lifecycle.*
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.data.remote.model.wishlist.DataWishlist
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.data.repository.WishlistRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val ticketRepository: TicketRepository,
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _wishlist = MutableLiveData<Resource<List<DataWishlist>>>()
    val wishlist: LiveData<Resource<List<DataWishlist>>> get() = _wishlist

    private val _ticket = MutableLiveData<List<DataTicket>>()
    val ticket: LiveData<List<DataTicket>> get() = _ticket

    fun getWishlists(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _wishlist.postValue(Resource.Loading())
        val wishlist = wishlistRepository.getWishlists()
        val wishlists = mutableListOf<DataWishlist>()
        val ticketList = mutableListOf<DataTicket>()
        val user = userRepository.getUserByToken(token).payload
        wishlist.payload?.forEach {
            if (it.userId.toString().equals(user!!.data!!.id.toString(), false)) {
                wishlists.add(it)
                ticketList.add(ticketRepository.getTicketById(it.ticketId!!).payload!!.data!!)
            } else {
                wishlists.clear()
                ticketList.clear()
            }
        }
        if (wishlists.size > 0) {
            viewModelScope.launch(Dispatchers.Main) {
                _wishlist.postValue(Resource.Success(wishlists))
                _ticket.postValue(ticketList)
            }
        } else {
            _wishlist.postValue(Resource.Empty())
        }
    }

    fun getToken(): LiveData<String> = dataStoreManager.getToken.asLiveData()
}