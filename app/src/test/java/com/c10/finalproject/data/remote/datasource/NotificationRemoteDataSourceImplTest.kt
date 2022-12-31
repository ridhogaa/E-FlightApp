package com.c10.finalproject.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.model.notification.GetNotificationResponse
import com.c10.finalproject.data.remote.service.ApiServiceNotification
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.mock

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class NotificationRemoteDataSourceImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: NotificationRemoteDataSource
    private lateinit var apiServiceNotification: ApiServiceNotification

    @Before
    fun setUp() {
        apiServiceNotification = mock()
        dataSource = NotificationRemoteDataSourceImpl(apiServiceNotification)
    }

    @Test
    fun getNotifications() = runBlocking {
        val correct: GetNotificationResponse = mockk()
        Mockito.`when`(apiServiceNotification.getNotifications()).thenReturn(correct)
        val response = dataSource.getNotifications()
        assertEquals(response, correct)
    }
}