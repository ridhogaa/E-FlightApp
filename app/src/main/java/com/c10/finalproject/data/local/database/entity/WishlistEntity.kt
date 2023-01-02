package com.c10.finalproject.data.local.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Parcelize
@Entity(tableName = "favorite_ticket")
class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id_favorite")
    var idFavorite: Int? = null,
    @field:ColumnInfo(name = "ticketId")
    var ticketId: Int? = null,
    @field:ColumnInfo(name = "userId")
    var userId: Int? = null,
    @field:ColumnInfo(name = "airplane_name")
    var airplaneName: String? = null,
    @field:ColumnInfo(name = "departure_time")
    var departureTime: String? = null,
    @field:ColumnInfo(name = "arrival_time")
    var arrivalTime: String? = null,
    @field:ColumnInfo(name = "return_time")
    var returnTime: String? = null,
    @field:ColumnInfo(name = "arrival2_time")
    var arrival2Time: String? = null,
    @field:ColumnInfo(name = "price")
    var price: Int? = null,
    @field:ColumnInfo(name = "category")
    var category: String? = null,
    @field:ColumnInfo(name = "origin")
    var origin: String? = null,
    @field:ColumnInfo(name = "destination")
    var destination: String? = null,
) : Parcelable

//
// idFavorite
// ticketId:
// userId
// airplaneName
// departureTime
// arrivalTime
// returnTime
// arrival2Time
// price
// category
// origin
// destination