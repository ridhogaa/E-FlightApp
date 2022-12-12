package com.c10.finalproject.data.remote.auth

import com.c10.finalproject.data.remote.auth.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceAuth {

    @POST("api/auth/login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body registerBody: RegisterBody): Response<RegisterResponse>

    @GET("api/auth/user")
    suspend fun getUserByToken(@Header("Authorization") token: String): Response<GetUserResponse>

}