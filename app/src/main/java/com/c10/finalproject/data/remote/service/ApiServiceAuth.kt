package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.auth.LoginBody
import com.c10.finalproject.data.remote.model.auth.LoginResponse
import com.c10.finalproject.data.remote.model.auth.RegisterBody
import com.c10.finalproject.data.remote.model.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
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

}