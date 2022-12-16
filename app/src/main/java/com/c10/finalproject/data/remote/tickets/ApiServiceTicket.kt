package com.c10.finalproject.data.remote.tickets

import com.c10.finalproject.data.remote.tickets.model.GetTicketResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceTicket {

    @GET("api/v1/tickets")
    suspend fun getTicket(): GetTicketResponse

}