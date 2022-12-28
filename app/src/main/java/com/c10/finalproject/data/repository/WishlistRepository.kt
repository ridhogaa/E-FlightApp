package com.c10.finalproject.data.repository

import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.local.datasource.WishlistLocalDataSource
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WishlistRepository {

    suspend fun getFavoriteTickets(userId: Int): Resource<List<WishlistEntity>>
    suspend fun isFavorite(ticketId: Int, userId: Int): Boolean
    suspend fun addFavorite(favoriteTicket: WishlistEntity)
    suspend fun deleteFavorite(ticketId: Int, userId: Int)
}

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistLocalDataSource: WishlistLocalDataSource
) : WishlistRepository {

    override suspend fun getFavoriteTickets(userId: Int): Resource<List<WishlistEntity>> =
        proceed {
            wishlistLocalDataSource.getFavoriteTickets(userId).map {
                WishlistEntity(
                    it.idFavorite,
                    it.ticketId,
                    it.userId,
                    it.airplaneName,
                    it.departureTime,
                    it.arrivalTime,
                    it.returnTime,
                    it.arrival2Time,
                    it.price,
                    it.category,
                    it.origin,
                    it.destination
                )
            }
        }


    override suspend fun isFavorite(ticketId: Int, userId: Int): Boolean =
        wishlistLocalDataSource.isFavorite(ticketId, userId)

    override suspend fun addFavorite(favoriteTicket: WishlistEntity) =
        wishlistLocalDataSource.addFavorite(favoriteTicket)

    override suspend fun deleteFavorite(ticketId: Int, userId: Int) =
        wishlistLocalDataSource.deleteFavorite(ticketId, userId)

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}