package com.c10.finalproject.data.remote.model.order


import com.google.gson.annotations.SerializedName

data class PostOrderResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("ticketId")
    val ticketId: Int?,
    @SerializedName("order_date")
    val orderDate: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String?
)