package com.c10.finalproject.data.remote.tickets.datasource

import com.c10.finalproject.data.remote.tickets.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.model.GetTicketResponse
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRemoteDataSource {
    suspend fun getTicket(): GetTicketResponse
}

class TicketRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    TicketRemoteDataSource {

    override suspend fun getTicket(): GetTicketResponse = apiServiceTicket.getTicket()
}