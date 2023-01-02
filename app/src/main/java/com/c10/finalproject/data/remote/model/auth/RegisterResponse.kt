package com.c10.finalproject.data.remote.model.auth


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("accessToken")
    val accessToken: String?
)