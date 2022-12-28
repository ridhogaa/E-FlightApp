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
class FavoriteTicketEntity(
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

//"airplane_name": "Garuda Indonesia",
//"departure_time": "2022-12-27T16:41:27.805Z",
//"arrival_time": "2022-12-27T18:41:32.805Z",
//"return_time": null,
//"arrival2_time": null,
//"price": 380000,
//"category": "one_way",
//"origin": "Jakarta",
//"destination": "Medan",