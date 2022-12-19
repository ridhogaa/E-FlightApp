package com.c10.finalproject.data.remote.user.model

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
    val noKtp: Any?,
    @SerializedName("username")
    val username: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("contact")
    val contact: Any?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: Any?,
    @SerializedName("address")
    val address: Any?,
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