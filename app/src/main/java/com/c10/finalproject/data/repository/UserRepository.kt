package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.UserRemoteDataSource
import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserRepository {
    suspend fun getUserByToken(token: String): Resource<GetUserResponse>
    suspend fun updateUserById(id: Int, bodyUpdateUser: BodyUpdateUser)
}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUserByToken(token: String): Resource<GetUserResponse> = proceed {
        userRemoteDataSource.getUserByToken(token)
    }

    override suspend fun updateUserById(id: Int, bodyUpdateUser: BodyUpdateUser) =
        userRemoteDataSource.updateUserById(id, bodyUpdateUser)

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}