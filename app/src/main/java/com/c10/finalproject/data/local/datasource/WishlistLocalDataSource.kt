package com.c10.finalproject.data.local.datasource

import com.c10.finalproject.data.local.database.dao.WishlistDao
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WishlistLocalDataSource {
    suspend fun getFavoriteTickets(userId: Int): List<WishlistEntity>
    suspend fun isFavorite(ticketId: Int, userId: Int): Boolean
    suspend fun addFavorite(favoriteTicket: WishlistEntity)
    suspend fun deleteFavorite(ticketId: Int, userId: Int)
}

class WishlistLocalDataSourceImpl @Inject constructor(private val wishlistDao: WishlistDao) :
    WishlistLocalDataSource {
    override suspend fun getFavoriteTickets(userId: Int): List<WishlistEntity> =
        wishlistDao.getFavoriteTickets(userId)

    override suspend fun isFavorite(ticketId: Int, userId: Int): Boolean =
        wishlistDao.isFavorite(ticketId, userId)

    override suspend fun addFavorite(favoriteTicket: WishlistEntity) =
        wishlistDao.addFavorite(favoriteTicket)

    override suspend fun deleteFavorite(ticketId: Int, userId: Int) =
        wishlistDao.deleteFavorite(ticketId, userId)

}