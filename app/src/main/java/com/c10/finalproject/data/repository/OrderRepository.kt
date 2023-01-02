package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.OrderRemoteDataSource
import com.c10.finalproject.data.remote.model.order.DataOrder
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface OrderRepository {
    suspend fun addOrder(token: String, id: Int): Resource<PostOrderResponse>
    suspend fun getOrders(): Resource<List<DataOrder>>
}

class OrderRepositoryImpl @Inject constructor(private val orderRemoteDataSource: OrderRemoteDataSource) :
    OrderRepository {
    override suspend fun addOrder(token: String, id: Int): Resource<PostOrderResponse> = proceed {
        orderRemoteDataSource.addOrder(token, id)
    }

    override suspend fun getOrders(): Resource<List<DataOrder>> = proceed {
        orderRemoteDataSource.getOrders().data?.map {
            DataOrder(
                it?.id,
                it?.ticketId,
                it?.userId,
                it?.orderDate,
                it?.createdAt,
                it?.updatedAt
            )
        }!!
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}