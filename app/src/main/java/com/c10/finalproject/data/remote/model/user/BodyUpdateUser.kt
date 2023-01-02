package com.c10.finalproject.data.remote.model.user


import com.google.gson.annotations.SerializedName

data class BodyUpdateUser(
    @SerializedName("noKtp")
    val noKtp: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("contact")
    val contact: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("photoProfile")
    val photoProfile: String? = null,
)