package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.HistoriesRemoteDataSource
import com.c10.finalproject.data.remote.tickets.model.histories.DataHistories
import com.c10.finalproject.data.remote.tickets.model.histories.DataUsers
import com.c10.finalproject.wrapper.Resource

import javax.inject.Inject

interface HistoriesRepository {

    suspend fun getHistories(): Resource<List<DataHistories>>

    suspend fun getUsers(): Resource<List<DataUsers>>

}

class HistoriesRepositoryImpl @Inject constructor(
    private val dataSource: HistoriesRemoteDataSource,
) :
    HistoriesRepository {

    override suspend fun getHistories(): Resource<List<DataHistories>> {
        return proceed {
            dataSource.getHistories().data?.map {
                DataHistories(
                    id = it?.id,
                    ticketId = it?.ticketId,
                    userId = it?.userId,
                    orderDate = it?.orderDate,
                    createdAt = it?.createdAt,
                    updatedAt = it?.updatedAt,
                )
            }!!
        }
    }

    override suspend fun getUsers(): Resource<List<DataUsers>> {
        return proceed {
            dataSource.getUsers().data?.map {
                DataUsers(
                    id = it.id,
                    address = it.address,
                    contact = it.contact,
                    dateOfBirth = it.dateOfBirth,
                    email = it.email,
                    gender = it.gender,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    encryptedPassword = it.encryptedPassword,
                    name = it.name,
                    noKtp = it.noKtp,
                    roleId = it.roleId,
                    photoProfile = it.photoProfile,
                    username =  it.username
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