package com.c10.finalproject.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.c10.finalproject.data.local.database.entity.WishlistEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Dao
interface WishlistDao {

    @Query("SELECT * from favorite_ticket where userId = :userId")
    suspend fun getFavoriteTickets(userId: Int): List<WishlistEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_ticket WHERE ticketId = :ticketId AND userId = :userId)")
    suspend fun isFavorite(ticketId: Int, userId: Int): Boolean

    @Insert
    suspend fun addFavorite(favoriteTicket: WishlistEntity)

    @Query("DELETE FROM favorite_ticket WHERE ticketId=:ticketId AND userId=:userId")
    suspend fun deleteFavorite(ticketId: Int, userId: Int)

}