package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.order.GetOrderResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceOrder {

    @POST("api/v1/tickets/{id}/order")
    suspend fun addOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): PostOrderResponse

    @GET("api/v1/orders")
    suspend fun getOrders(): GetOrderResponse

}