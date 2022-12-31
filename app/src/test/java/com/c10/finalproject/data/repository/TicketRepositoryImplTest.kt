package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.datasource.TicketRemoteDataSourceImpl
import com.c10.finalproject.data.remote.model.ticket.GetTicketResponse
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.remote.service.ApiServiceTicket
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

class TicketRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: TicketRepository
    private lateinit var apiServiceTicket: ApiServiceTicket

    @Before
    fun setUp() {
        apiServiceTicket = mock()
        repository = TicketRepositoryImpl(TicketRemoteDataSourceImpl(apiServiceTicket))
    }

    @Test
    fun getTickets() = runBlocking {
        val correct = mockk<GetTicketResponse>()
        Mockito.`when`(apiServiceTicket.getTickets()).thenReturn(correct)
        val response = repository.getTickets().payload
        assertNotEquals(response, correct)
    }

    @Test
    fun getTicketById() = runBlocking {
        val correct = mockk<GetTicketByIdResponse>()
        Mockito.`when`(apiServiceTicket.getTicketById(any())).thenReturn(correct)
        val response = repository.getTicketById(any()).payload!!
        assertEquals(response, correct)
    }
}