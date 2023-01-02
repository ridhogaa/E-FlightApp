package com.c10.finalproject.data.remote.model.auth


import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("error")
    val error: Error?
)

data class Error(
    @SerializedName("name")
    val name: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("details")
    val details: Details?
)

data class Details(
    @SerializedName("email")
    val email: String?
)