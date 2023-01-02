package com.c10.finalproject.data.remote.tickets.model.ticket.add

import com.google.gson.annotations.SerializedName

data class AddTicketResponse (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("airplane_name")
    val airplaneName: String?,
    @SerializedName("departure_time")
    val departureTime: String?,
    @SerializedName("arrival_time")
    val arrivalTime: String?,
    @SerializedName("return_time")
    val returnTime: String?,
    @SerializedName("arrival2_time")
    val arrival2Time: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("destination")
    val destination: String?,
    @SerializedName("createdBy")
    val createdBy: Int?,
    @SerializedName("updatedBy")
    val updatedBy: Int?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
