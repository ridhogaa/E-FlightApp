package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.model.histories.GetHistoriesResponse
import com.c10.finalproject.data.remote.tickets.model.histories.GetUsersResponse
import javax.inject.Inject

interface HistoriesRemoteDataSource {
    suspend fun getHistories(): GetHistoriesResponse

    suspend fun getUsers(): GetUsersResponse

}

class HistoriesRemoteDataSourceImpl @Inject constructor(private val apiServiceTicket: ApiServiceTicket) :
    HistoriesRemoteDataSource {

    override suspend fun getHistories(): GetHistoriesResponse = apiServiceTicket.getHistories()

    override suspend fun getUsers(): GetUsersResponse = apiServiceTicket.getUsers()


}