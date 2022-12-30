package com.c10.finalproject.data.remote.tickets.model.histories

import com.google.gson.annotations.SerializedName

data class GetHistoriesResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: List<DataHistories?>?
)

data class DataHistories(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("ticketId")
    val ticketId: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("order_date")
    val orderDate: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)