package com.c10.finalproject.data.remote.tickets

import com.c10.finalproject.data.remote.tickets.model.GetTicketByIdResponse
import com.c10.finalproject.data.remote.tickets.model.GetTicketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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