package com.c10.finalproject.data.remote.user

import com.c10.finalproject.data.remote.user.model.GetUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceUser {

    @GET("api/auth/user")
    suspend fun getUserByToken(@Header("Authorization") token: String): Response<GetUserResponse>
}