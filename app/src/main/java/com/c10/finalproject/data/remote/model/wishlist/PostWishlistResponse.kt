package com.c10.finalproject.data.remote.model.wishlist


import com.google.gson.annotations.SerializedName

data class PostWishlistResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: DataPostWishlist?
)

data class DataPostWishlist(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("ticketId")
    val ticketId: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("destroyedAt")
    val destroyedAt: Any?
)