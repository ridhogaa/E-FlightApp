package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.notification.GetNotificationResponse
import com.c10.finalproject.data.remote.model.order.GetOrderResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import retrofit2.http.*

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceTicket {

    @GET("api/v1/tickets")
    suspend fun getTickets(): GetTicketResponse

    @GET("api/v1/tickets/{id}")
    suspend fun getTicketById(@Path("id") id: Int): GetTicketByIdResponse

}