package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.datasource.OrderRemoteDataSourceImpl
import com.c10.finalproject.data.remote.datasource.TicketRemoteDataSourceImpl
import com.c10.finalproject.data.remote.model.order.GetOrderResponse
import com.c10.finalproject.data.remote.model.order.PostOrderResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import com.c10.finalproject.data.remote.service.ApiServiceOrder
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

class OrderRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: OrderRepository
    private lateinit var apiServiceOrder: ApiServiceOrder

    @Before
    fun setUp() {
        apiServiceOrder = mock()
        repository = OrderRepositoryImpl(OrderRemoteDataSourceImpl(apiServiceOrder))
    }

    @Test
    fun addOrder() = runBlocking {
        val correct: PostOrderResponse = mockk()
        Mockito.`when`(apiServiceOrder.addOrder(any(), any())).thenReturn(correct)
        val response = repository.addOrder(any(), any()).payload
        assertNotEquals(response, correct)
    }

    @Test
    fun getOrders() = runBlocking {
        val correct: GetOrderResponse = mockk()
        Mockito.`when`(apiServiceOrder.getOrders()).thenReturn(correct)
        val response = repository.getOrders().payload
        assertNotEquals(response, correct)
    }
}