package com.c10.finalproject.data.remote.model.wishlist


import com.google.gson.annotations.SerializedName

data class GetWishlistResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: List<DataWishlist?>?
)

data class DataWishlist(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("ticketId")
    val ticketId: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("destroyedAt")
    val destroyedAt: Any?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)