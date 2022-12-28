package com.c10.finalproject.data.repository

import com.c10.finalproject.data.remote.datasource.NotificationRemoteDataSource
import com.c10.finalproject.data.remote.model.notification.DataNotification
import com.c10.finalproject.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface NotificationRepository {
    suspend fun getNotifications(): Resource<List<DataNotification>>
}

class NotificationRepositoryImpl @Inject constructor(private val notificationRemoteDataSource: NotificationRemoteDataSource) :
    NotificationRepository {
    override suspend fun getNotifications(): Resource<List<DataNotification>> = proceed {
        notificationRemoteDataSource.getNotifications().data?.map {
            DataNotification(
                it?.id,
                it?.orderId,
                it?.userId,
                it?.createdAt,
                it?.updatedAt
            )
        }!!
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}