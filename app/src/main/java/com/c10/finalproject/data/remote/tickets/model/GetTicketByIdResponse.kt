package com.c10.finalproject.data.remote.tickets.model


import com.google.gson.annotations.SerializedName

data class GetTicketByIdResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: DataTicket?
)

data class DataTicket(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("airplane_name")
    val airplaneName: String?,
    @SerializedName("departure_time")
    val departureTime: String?,
    @SerializedName("arrival_time")
    val arrivalTime: String?,
    @SerializedName("return_time")
    val returnTime: Any?,
    @SerializedName("arrival2_time")
    val arrival2Time: Any?,
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