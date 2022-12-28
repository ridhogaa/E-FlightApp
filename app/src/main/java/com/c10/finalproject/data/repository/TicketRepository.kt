package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.TicketRemoteDataSource
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRepository {
    suspend fun getTickets(): Resource<List<Data>>
    suspend fun getTicketById(id: Int): Resource<GetTicketByIdResponse>
}

class TicketRepositoryImpl @Inject constructor(private val dataSource: TicketRemoteDataSource) :
    TicketRepository {
    override suspend fun getTickets(): Resource<List<Data>> {
        return proceed {
            dataSource.getTickets().data?.map {
                Data(
                    id = it?.id,
                    airplaneName = it?.airplaneName,
                    departureTime = it?.departureTime,
                    arrivalTime = it?.arrivalTime,
                    returnTime = it?.returnTime,
                    arrival2Time = it?.arrival2Time,
                    price = it?.price,
                    category = it?.category,
                    origin = it?.origin,
                    destination = it?.destination,
                    createdBy = it?.createdBy,
                    updatedBy = it?.updatedBy,
                    deletedAt = it?.deletedAt,
                    createdAt = it?.createdAt,
                    updatedAt = it?.updatedAt,
                )
            }!!
        }
    }

    override suspend fun getTicketById(id: Int): Resource<GetTicketByIdResponse> {
        return proceed {
            dataSource.getTicketById(id)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}