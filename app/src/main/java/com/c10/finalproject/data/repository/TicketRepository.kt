package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.tickets.datasource.TicketRemoteDataSource
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRepository {
    suspend fun getTicket(): Resource<List<Data>>
}

class TicketRepositoryImpl @Inject constructor(private val dataSource: TicketRemoteDataSource) :
    TicketRepository {
    override suspend fun getTicket(): Resource<List<Data>> {
        return proceed {
            dataSource.getTicket().data?.map {
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

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}