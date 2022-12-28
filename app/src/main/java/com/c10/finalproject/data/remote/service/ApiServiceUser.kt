package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import retrofit2.http.*

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceUser {

    @GET("api/auth/user")
    suspend fun getUserByToken(@Header("Authorization") token: String): GetUserResponse

    @PUT("api/v1/user/update/{id}")
    suspend fun updateUserById(@Path("id") id: Int, @Body bodyUpdateUser: BodyUpdateUser)
}