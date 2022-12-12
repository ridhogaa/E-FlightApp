package com.c10.finalproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.c10.finalproject.data.remote.auth.ApiServiceAuth
import com.c10.finalproject.data.remote.auth.model.LoginBody
import com.c10.finalproject.data.remote.auth.model.LoginResponse
import com.c10.finalproject.data.remote.auth.model.RegisterBody
import com.c10.finalproject.data.remote.auth.model.RegisterResponse
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.lang.Exception

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
        val expected = LoginResponse("test", "test", "test", "test")
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