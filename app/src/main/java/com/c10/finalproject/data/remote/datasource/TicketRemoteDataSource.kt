package com.c10.finalproject.data.remote.tickets.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.add.AddTicketBody
import com.c10.finalproject.data.remote.tickets.model.ticket.add.AddTicketResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.delete.DeleteTicketResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketBody
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRemoteDataSource {
    suspend fun getTickets(): GetTicketResponse
    suspend fun getTicketById(id: Int): GetTicketByIdResponse

    suspend fun addTicket(token: String, addTicketBody: AddTicketBody): Response<AddTicketResponse>

    suspend fun updateTicket(token: String, id: Int, updateTicketBody: UpdateTicketBody): Response<UpdateTicketResponse>

    suspend fun deleteTicket(token: String, id: Int): Response<DeleteTicketResponse>

}

class TicketRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    TicketRemoteDataSource {

    override suspend fun getTickets(): GetTicketResponse = apiServiceTicket.getTickets()
    override suspend fun getTicketById(id: Int): GetTicketByIdResponse =
        apiServiceTicket.getTicketById(id)

    override suspend fun addTicket(token: String, addTicketBody: AddTicketBody): Response<AddTicketResponse> =
        apiServiceTicket.addTicket(token, addTicketBody)

    override suspend fun updateTicket(token: String, id: Int, updateTicketBody: UpdateTicketBody): Response<UpdateTicketResponse> =
        apiServiceTicket.updateTicket(token, id, updateTicketBody)

    override suspend fun deleteTicket(token: String, id: Int): Response<DeleteTicketResponse> =
        apiServiceTicket.deleteTicket(token, id)


}