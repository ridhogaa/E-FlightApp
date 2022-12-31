package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRemoteDataSource {
    suspend fun getTickets(): GetTicketResponse
    suspend fun getTicketById(id: Int): GetTicketByIdResponse
}

class TicketRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    TicketRemoteDataSource {

    override suspend fun getTickets(): GetTicketResponse = apiServiceTicket.getTickets()
    override suspend fun getTicketById(id: Int): GetTicketByIdResponse =
        apiServiceTicket.getTicketById(id)
}