package com.c10.finalproject.data.remote.auth.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("accessToken")
    val accessToken: String?
)