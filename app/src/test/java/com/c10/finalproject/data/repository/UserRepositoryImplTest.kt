package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.remote.datasource.UserRemoteDataSourceImpl
import com.c10.finalproject.data.remote.model.user.Data
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.remote.service.ApiServiceUser
import com.c10.finalproject.wrapper.Resource
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class UserRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: UserRepository

    @Mock
    private lateinit var apiServiceUser: ApiServiceUser

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiServiceUser = mock()
        repository = UserRepositoryImpl(UserRemoteDataSourceImpl(apiServiceUser))
    }

    @Test
    fun getUserByToken() = runBlocking {
        val correct = GetUserResponse("test", "test", data = any())
        Mockito.`when`(apiServiceUser.getUserByToken("test")).thenReturn(correct)
        val response = repository.getUserByToken("test").payload
        assertNotEquals(response, correct)
    }

    @Test
    fun updateUserById() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(apiServiceUser.updateUserById(any(), bodyUpdateUser = any()))
            .thenReturn(correct)
        val response = repository.updateUserById(any(), bodyUpdateUser = any())
        assertEquals(response, correct)
    }
}