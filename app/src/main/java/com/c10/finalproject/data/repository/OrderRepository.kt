package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.c10.finalproject.data.remote.tickets.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.datasource.OrderRemoteDataSource
import com.c10.finalproject.data.remote.tickets.model.PostOrderResponse
import com.c10.finalproject.wrapper.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface OrderRepository {
    suspend fun addOrder(token: String, id: Int): Resource<PostOrderResponse>
}

class OrderRepositoryImpl @Inject constructor(private val orderRemoteDataSource: OrderRemoteDataSource) :
    OrderRepository {
    override suspend fun addOrder(token: String, id: Int): Resource<PostOrderResponse> = proceed {
        orderRemoteDataSource.addOrder(token, id)
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}