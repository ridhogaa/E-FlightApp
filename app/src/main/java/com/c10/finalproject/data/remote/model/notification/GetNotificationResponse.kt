package com.c10.finalproject.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class GetNotificationResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: List<DataNotification?>?
)

data class DataNotification(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("orderId")
    val orderId: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)