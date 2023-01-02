package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.c10.finalproject.data.remote.service.ApiServiceAuth
import com.c10.finalproject.data.remote.model.auth.LoginBody
import com.c10.finalproject.data.remote.model.auth.LoginResponse
import com.c10.finalproject.data.remote.model.auth.RegisterBody
import com.c10.finalproject.data.remote.model.auth.RegisterResponse
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class AuthRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var apiServiceAuth: ApiServiceAuth

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authRepository = AuthRepositoryImpl(apiServiceAuth)
    }

    @Test
    fun login() = runBlocking {
        val expected = LoginResponse("test", "test", "test", "test", "test", 5)
        Mockito.`when`(apiServiceAuth.login(LoginBody("test", "test")))
            .thenReturn(Response.success(expected))
        authRepository.login(LoginBody("test", "test")).collect {
            it.onSuccess { response ->
                assertEquals(expected, response)
                println("${expected} expected")
                println("${response} actual")
            }
        }
    }

    @Test
    fun register() = runBlocking {
        val expected = RegisterResponse("test", "test", "test", "test")
        Mockito.`when`(apiServiceAuth.register(RegisterBody("test", "test", "test")))
            .thenReturn(Response.success(expected))
        authRepository.register(RegisterBody("test", "test", "test")).collect {
            it.onSuccess { response ->
                assertEquals(expected, response)
                println("${expected} expected")
                println("${response} actual")
            }
        }
    }
}