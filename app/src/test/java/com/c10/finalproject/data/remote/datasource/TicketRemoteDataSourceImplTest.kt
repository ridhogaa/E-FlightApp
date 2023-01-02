package com.c10.finalproject.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import com.c10.finalproject.data.remote.service.ApiServiceTicket
import com.c10.finalproject.data.repository.TicketRepository
import com.c10.finalproject.data.repository.TicketRepositoryImpl
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

class TicketRemoteDataSourceImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: TicketRemoteDataSource
    private lateinit var apiServiceTicket: ApiServiceTicket

    @Before
    fun setUp() {
        apiServiceTicket = mock()
        dataSource = TicketRemoteDataSourceImpl(apiServiceTicket)
    }

    @Test
    fun getTickets() = runBlocking {
        val correct = mockk<GetTicketResponse>()
        Mockito.`when`(apiServiceTicket.getTickets()).thenReturn(correct)
        val response = dataSource.getTickets()
        assertEquals(response, correct)
    }

    @Test
    fun getTicketById() = runBlocking {
        val correct = mockk<GetTicketByIdResponse>()
        Mockito.`when`(apiServiceTicket.getTicketById(any())).thenReturn(correct)
        val response = dataSource.getTicketById(1)
        assertEquals(response, correct)
    }
}