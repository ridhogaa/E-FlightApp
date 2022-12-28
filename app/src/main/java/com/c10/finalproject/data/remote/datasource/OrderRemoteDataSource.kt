package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.model.order.GetOrderResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.data.remote.service.ApiServiceOrder
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface OrderRemoteDataSource {
    suspend fun addOrder(token: String, id: Int): PostOrderResponse
    suspend fun getOrders(): GetOrderResponse
}

class OrderRemoteDataSourceImpl @Inject constructor(private val apiServiceOrder: ApiServiceOrder) :
    OrderRemoteDataSource {

    override suspend fun addOrder(token: String, id: Int): PostOrderResponse =
        apiServiceOrder.addOrder(token, id)

    override suspend fun getOrders(): GetOrderResponse = apiServiceOrder.getOrders()

}