package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.notification.GetNotificationResponse
import retrofit2.http.GET

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceNotification {

    @GET("api/v1/notifications")
    suspend fun getNotifications(): GetNotificationResponse

}