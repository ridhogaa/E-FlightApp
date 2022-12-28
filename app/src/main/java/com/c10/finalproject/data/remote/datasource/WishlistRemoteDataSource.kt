package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.model.wishlist.GetWishlistResponse
import com.c10.finalproject.data.remote.model.wishlist.PostWishlistResponse
import com.c10.finalproject.data.remote.service.ApiServiceWishlist
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WishlistRemoteDataSource {
    suspend fun getWishlists(): GetWishlistResponse
    suspend fun addWishlist(token: String, id: Int): PostWishlistResponse
    suspend fun deleteWishlist(token: String, id: Int)
}

class WishlistRemoteDataSourceImpl @Inject constructor(private val apiServiceWishlist: ApiServiceWishlist) :
    WishlistRemoteDataSource {
    override suspend fun getWishlists(): GetWishlistResponse = apiServiceWishlist.getWishlists()

    override suspend fun addWishlist(token: String, id: Int): PostWishlistResponse =
        apiServiceWishlist.addWishlist(token, id)

    override suspend fun deleteWishlist(token: String, id: Int) =
        apiServiceWishlist.deleteWishlist(token, id)
}