package com.c10.finalproject.data.remote.tickets

import com.c10.finalproject.data.remote.auth.model.RegisterResponse
import com.c10.finalproject.data.remote.tickets.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("api/v1/tickets/{id}/order")
    suspend fun addOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): PostOrderResponse

    @POST("api/v1/tickets/add")
    suspend fun addTicket(
        @Header("Authorization") token: String,
        @Body addTicketBody: AddTicketBody
    ): Response<AddTicketResponse>


    @PUT("api/v1/tickets/update/{id}")
    suspend fun updateTicket(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): UpdateTicketResponse



}