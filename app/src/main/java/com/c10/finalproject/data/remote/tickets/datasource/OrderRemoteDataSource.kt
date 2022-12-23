package com.c10.finalproject.data.remote.tickets.datasource

import com.c10.finalproject.data.remote.tickets.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.model.PostOrderResponse
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface OrderRemoteDataSource {
    suspend fun addOrder(token: String, id: Int): PostOrderResponse
}

class OrderRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    OrderRemoteDataSource {

    override suspend fun addOrder(token: String, id: Int): PostOrderResponse =
        apiServiceTicket.addOrder(token, id)

}