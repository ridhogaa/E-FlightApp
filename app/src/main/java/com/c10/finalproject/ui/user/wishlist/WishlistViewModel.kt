package com.c10.finalproject.ui.user.wishlist

import androidx.lifecycle.*
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.data.repository.WishlistRepository
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _wishlist = MutableLiveData<Resource<List<WishlistEntity>>>()
    val wishlist: LiveData<Resource<List<WishlistEntity>>> get() = _wishlist

    fun getWishlist(userId: Int) = CoroutineScope(Dispatchers.IO).launch {
        _wishlist.postValue(Resource.Loading())
//        delay(1000)
        if (wishlistRepository.getFavoriteTickets(userId).payload?.isNotEmpty()!!) {
            _wishlist.postValue(wishlistRepository.getFavoriteTickets(userId))
        } else {
            _wishlist.postValue(Resource.Empty())
        }
    }

    fun getToken() = dataStoreManager.getToken.asLiveData()

    fun getId() = dataStoreManager.getId.asLiveData()
}