package com.c10.finalproject.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val `data`: Data?
)

data class Data(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("noKtp")
    val noKtp: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("contact")
    val contact: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("photoProfile")
    val photoProfile: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String?,
    @SerializedName("roleId")
    val roleId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)