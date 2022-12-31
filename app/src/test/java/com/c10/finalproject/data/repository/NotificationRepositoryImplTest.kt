package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.datasource.NotificationRemoteDataSourceImpl
import com.c10.finalproject.data.remote.model.notification.GetNotificationResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.data.remote.service.ApiServiceNotification
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class NotificationRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: NotificationRepository
    private lateinit var apiServiceNotification: ApiServiceNotification

    @Before
    fun setUp() {
        apiServiceNotification = mock()
        repository =
            NotificationRepositoryImpl(NotificationRemoteDataSourceImpl(apiServiceNotification))
    }

    @Test
    fun getNotifications() = runBlocking {
        val correct: GetNotificationResponse = mockk()
        Mockito.`when`(apiServiceNotification.getNotifications()).thenReturn(correct)
        val response = repository.getNotifications().payload
        assertNotEquals(response, correct)
    }
}