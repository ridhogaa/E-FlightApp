package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.GetUserResponse
import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserRespository {
    suspend fun getUserByToken(token: String): Flow<Result<GetUserResponse>>
}

class UserRepositoryImpl @Inject constructor(
    private val apiServiceAuth: ApiServiceAuth
) : UserRespository {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getUserByToken(token: String): Flow<Result<GetUserResponse>> =
        flow {
            try {
                val response = apiServiceAuth.getUserByToken("Bearer $token")
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