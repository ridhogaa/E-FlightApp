package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceUser
import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserRemoteDataSource {
    suspend fun getUserByToken(token: String): GetUserResponse
    suspend fun updateUserById(id: Int, bodyUpdateUser: BodyUpdateUser)
}

class UserRemoteDataSourceImpl @Inject constructor(private val apiServiceUser: ApiServiceUser) :
    UserRemoteDataSource {
    override suspend fun getUserByToken(token: String): GetUserResponse =
        apiServiceUser.getUserByToken(token)

    override suspend fun updateUserById(id: Int, bodyUpdateUser: BodyUpdateUser) =
        apiServiceUser.updateUserById(id, bodyUpdateUser)

}