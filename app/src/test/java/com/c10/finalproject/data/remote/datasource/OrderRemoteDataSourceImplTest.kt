package com.c10.finalproject.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.model.order.GetOrderResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.data.remote.service.ApiServiceOrder
import com.c10.finalproject.data.repository.OrderRepositoryImpl
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

class OrderRemoteDataSourceImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var apiServiceOrder: ApiServiceOrder
    private lateinit var dataSource: OrderRemoteDataSource

    @Before
    fun setUp() {
        apiServiceOrder = mock()
        dataSource = OrderRemoteDataSourceImpl(apiServiceOrder)
    }

    @Test
    fun addOrder() = runBlocking {
        val correct = mockk<PostOrderResponse>()
        Mockito.`when`(apiServiceOrder.addOrder(any(), any())).thenReturn(correct)
        val response = dataSource.addOrder("test", 1)
        assertEquals(response, correct)
    }

    @Test
    fun getOrders() = runBlocking {
        val correct = mockk<GetOrderResponse>()
        Mockito.`when`(apiServiceOrder.getOrders()).thenReturn(correct)
        val response = dataSource.getOrders()
        assertEquals(response, correct)
    }
}