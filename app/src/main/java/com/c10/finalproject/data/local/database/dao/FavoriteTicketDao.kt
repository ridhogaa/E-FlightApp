package com.c10.finalproject.data.local.database.dao

import androidx.room.Insert
import androidx.room.Query
import com.c10.finalproject.data.local.database.entity.FavoriteTicketEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FavoriteTicketDao {

    @Query("SELECT * from favorite_ticket where userId = :userId")
    suspend fun getFavoriteTickets(userId: Int): List<FavoriteTicketEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_ticket WHERE ticketId = :ticketId AND userId = :userId)")
    suspend fun isFavorite(ticketId: Int, userId: Int): Boolean

    @Insert
    suspend fun addFavorite(favoriteTicket: FavoriteTicketEntity)

    @Query("DELETE FROM favorite_ticket WHERE ticketId=:ticketId AND userId=:userId")
    suspend fun deleteFavorite(ticketId: Int, userId: Int)

}