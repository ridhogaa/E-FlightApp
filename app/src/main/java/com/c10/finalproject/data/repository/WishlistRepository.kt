package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.WishlistRemoteDataSource
import com.c10.finalproject.data.remote.model.wishlist.DataWishlist
import com.c10.finalproject.data.remote.model.wishlist.PostWishlistResponse
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WishlistRepository {
    suspend fun getWishlists(): Resource<List<DataWishlist>>
    suspend fun addWishlist(token: String, id: Int): PostWishlistResponse
    suspend fun deleteWishlist(token: String, id: Int)
}

class WishlistRepositoryImpl @Inject constructor(private val wishlistRemoteDataSource: WishlistRemoteDataSource) :
    WishlistRepository {
    override suspend fun getWishlists(): Resource<List<DataWishlist>> = proceed {
        wishlistRemoteDataSource.getWishlists().data?.map {
            DataWishlist(
                it?.id,
                it?.ticketId,
                it?.userId,
                it?.destroyedAt,
                it?.createdAt,
                it?.updatedAt
            )
        }!!
    }

    override suspend fun addWishlist(token: String, id: Int) =
        wishlistRemoteDataSource.addWishlist(token, id)

    override suspend fun deleteWishlist(token: String, id: Int) =
        wishlistRemoteDataSource.deleteWishlist(token, id)

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}