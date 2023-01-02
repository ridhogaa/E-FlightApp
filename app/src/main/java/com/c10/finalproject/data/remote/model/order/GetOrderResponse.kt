package com.c10.finalproject.data.remote.model.order


import com.google.gson.annotations.SerializedName

data class GetOrderResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: List<DataOrder?>?
)

data class DataOrder(
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