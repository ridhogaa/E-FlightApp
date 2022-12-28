package com.c10.finalproject.data.remote.datasource

import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.remote.model.notification.GetNotificationResponse
import com.c10.finalproject.data.remote.service.ApiServiceNotification
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface NotificationRemoteDataSource {
    suspend fun getNotifications(): GetNotificationResponse
}

class NotificationRemoteDataSourceImpl @Inject constructor(private val apiServiceNotification: ApiServiceNotification) :
    NotificationRemoteDataSource {

    override suspend fun getNotifications(): GetNotificationResponse =
        apiServiceNotification.getNotifications()

}