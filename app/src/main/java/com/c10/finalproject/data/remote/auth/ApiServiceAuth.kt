package com.c10.finalproject.data.remote.auth

import com.c10.finalproject.data.remote.auth.model.LoginBody
import com.c10.finalproject.data.remote.auth.model.LoginResponse
import com.c10.finalproject.data.remote.auth.model.RegisterBody
import com.c10.finalproject.data.remote.auth.model.RegisterResponse
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