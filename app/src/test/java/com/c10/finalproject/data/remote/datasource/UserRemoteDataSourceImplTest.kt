package com.c10.finalproject.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.data.remote.service.ApiServiceUser
import com.c10.finalproject.data.repository.UserRepository
import com.c10.finalproject.data.repository.UserRepositoryImpl
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

class UserRemoteDataSourceImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: UserRemoteDataSource

    @Mock
    private lateinit var apiServiceUser: ApiServiceUser

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiServiceUser = mock()
        dataSource = UserRemoteDataSourceImpl(apiServiceUser)
    }

    @Test
    fun getUserByToken() = runBlocking {
        val correct = mockk<GetUserResponse>()
        Mockito.`when`(apiServiceUser.getUserByToken(any())).thenReturn(correct)
        val response = dataSource.getUserByToken("test")
        assertEquals(response, correct)
    }

    @Test
    fun updateUserById() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(apiServiceUser.updateUserById(any(), bodyUpdateUser = any()))
            .thenReturn(correct)
        val response = dataSource.updateUserById(5, BodyUpdateUser("test"))
        assertEquals(response, correct)
    }
}