package com.c10.finalproject.data.repository

import com.c10.finalproject.data.local.database.dao.WishlistDao
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.local.datasource.WishlistLocalDataSourceImpl
import com.c10.finalproject.wrapper.Resource
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class WishlistRepositoryImplTest {

    private lateinit var repository: WishlistRepository
    private lateinit var wishlistDao: WishlistDao

    @Before
    fun setUp() {
        wishlistDao = mock()
        repository = WishlistRepositoryImpl(WishlistLocalDataSourceImpl(wishlistDao))
    }

    @Test
    fun getFavoriteTickets() = runBlocking {
        val correct = mockk<List<WishlistEntity>>()
        Mockito.`when`(wishlistDao.getFavoriteTickets(any())).thenReturn(correct)
        val response = repository.getFavoriteTickets(any()).payload
        assertNotEquals(response, correct)
    }

    @Test
    fun isFavorite() = runBlocking {
        val correct = true
        Mockito.`when`(wishlistDao.isFavorite(any(), any())).thenReturn(correct)
        val response = repository.isFavorite(any(), any())
        assertEquals(response, correct)
    }

    @Test
    fun addFavorite() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(wishlistDao.addFavorite(favoriteTicket = any())).thenReturn(correct)
        val response = repository.addFavorite(favoriteTicket = any())
        assertEquals(response, correct)
    }

    @Test
    fun deleteFavorite() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(wishlistDao.deleteFavorite(any(), any())).thenReturn(correct)
        val response = repository.deleteFavorite(any(), any())
        assertEquals(response, correct)
    }
}