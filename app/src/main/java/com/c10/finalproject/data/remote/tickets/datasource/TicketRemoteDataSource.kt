package com.c10.finalproject.data.remote.tickets.datasource

import com.c10.finalproject.data.remote.tickets.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.model.AddTicketResponse
import com.c10.finalproject.data.remote.tickets.model.GetTicketByIdResponse
import com.c10.finalproject.data.remote.tickets.model.GetTicketResponse
import com.c10.finalproject.data.remote.tickets.model.UpdateTicketResponse
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRemoteDataSource {
    suspend fun getTickets(): GetTicketResponse
    suspend fun getTicketById(id: Int): GetTicketByIdResponse
    suspend fun updateTicket(token: String, id: Int): UpdateTicketResponse

}

class TicketRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    TicketRemoteDataSource {

    override suspend fun getTickets(): GetTicketResponse = apiServiceTicket.getTickets()
    override suspend fun getTicketById(id: Int): GetTicketByIdResponse =
        apiServiceTicket.getTicketById(id)

    override suspend fun updateTicket(token: String, id: Int): UpdateTicketResponse =
        apiServiceTicket.updateTicket(token, id)
}