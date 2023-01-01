package com.c10.finalproject.data.remote.tickets.model.histories


import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    @SerializedName("data")
    val `data`: List<DataUsers>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class DataUsers(
    @SerializedName("address")
    val address: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("noKtp")
    val noKtp: String,
    @SerializedName("photoProfile")
    val photoProfile: String,
    @SerializedName("roleId")
    val roleId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)