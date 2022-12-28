package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.auth.model.RegisterResponse
import com.c10.finalproject.data.remote.auth.model.ResponseError
import com.c10.finalproject.data.remote.tickets.ApiServiceTicket
import com.c10.finalproject.data.remote.tickets.datasource.TicketRemoteDataSource
import com.c10.finalproject.data.remote.tickets.model.*
import com.c10.finalproject.wrapper.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface TicketRepository {
    suspend fun getTickets(): Resource<List<Data>>
    suspend fun getTicketById(id: Int): Resource<GetTicketByIdResponse>

    suspend fun addTicket(
        token: String,
        addTicketBody: AddTicketBody
    ): Flow<Result<AddTicketResponse>>

    suspend fun updateTicket(token: String, id: Int): Resource<UpdateTicketResponse>

}

class TicketRepositoryImpl @Inject constructor(
    private val dataSource: TicketRemoteDataSource,
    private val apiServiceTicket: ApiServiceTicket
) :
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

    override suspend fun updateTicket(token: String, id: Int): Resource<UpdateTicketResponse> =
        proceed {
            dataSource.updateTicket(token, id)
        }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun addTicket(
        token: String,
        addTicketBody: AddTicketBody
    ): Flow<Result<AddTicketResponse>> =
        flow {
            try {
                val response = apiServiceTicket.addTicket(token, addTicketBody)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.success(it))
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ResponseError::class.java)
                    emit(Result.failure(Throwable(error.error?.message)))
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Result.failure(exception))
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