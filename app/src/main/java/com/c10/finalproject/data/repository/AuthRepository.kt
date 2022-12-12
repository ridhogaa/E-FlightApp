package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthRepository {
    suspend fun login(loginBody: LoginBody): Flow<Result<LoginResponse>>
    suspend fun register(registerBody: RegisterBody): Flow<Result<RegisterResponse>>
}

class AuthRepositoryImpl @Inject constructor(
    private val apiServiceAuth: ApiServiceAuth
) : AuthRepository {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun login(loginBody: LoginBody): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiServiceAuth.login(loginBody)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                }
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ResponseError::class.java)
                emit(Result.failure(Throwable(error.error?.message)))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(exception))
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun register(registerBody: RegisterBody): Flow<Result<RegisterResponse>> =
        flow {
            try {
                val response = apiServiceAuth.register(registerBody)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.success(it))
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ResponseError::class.java)
                    emit(Result.failure(Throwable(error.error?.message)))
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Result.failure(exception))
            }
        }
}