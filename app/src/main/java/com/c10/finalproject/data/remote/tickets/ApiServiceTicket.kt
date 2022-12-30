package com.c10.finalproject.data.remote.tickets

import com.c10.finalproject.data.remote.tickets.model.*
import com.c10.finalproject.data.remote.tickets.model.histories.GetHistoriesResponse
import com.c10.finalproject.data.remote.tickets.model.histories.GetUsersResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.add.AddTicketBody
import com.c10.finalproject.data.remote.tickets.model.ticket.add.AddTicketResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.delete.DeleteTicketResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketBody
import retrofit2.Response
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
        @Path("id") id: Int,
        @Body updateTicketBody: UpdateTicketBody

    ): Response<UpdateTicketResponse>

    @DELETE("api/v1/tickets/delete/{id}")
    suspend fun deleteTicket(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteTicketResponse>

    @GET("api/v1/histories")
    suspend fun getHistories(): GetHistoriesResponse

    @GET("api/v1/users")
    suspend fun getUsers(): GetUsersResponse



}