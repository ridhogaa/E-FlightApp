package com.c10.finalproject.data.local.datasource

import com.c10.finalproject.data.local.database.dao.WishlistDao
import com.c10.finalproject.data.local.database.entity.WishlistEntity
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

class WishlistLocalDataSourceImplTest {

    private lateinit var dataSource: WishlistLocalDataSource
    private lateinit var wishlistDao: WishlistDao

    @Before
    fun setUp() {
        wishlistDao = mock()
        dataSource = WishlistLocalDataSourceImpl(wishlistDao)
    }

    @Test
    fun getFavoriteTickets() = runBlocking {
        val correct = mockk<List<WishlistEntity>>()
        Mockito.`when`(wishlistDao.getFavoriteTickets(any())).thenReturn(correct)
        val response = dataSource.getFavoriteTickets(any())
        assertEquals(response, correct)
    }

    @Test
    fun isFavorite() = runBlocking {
        val correct = true
        Mockito.`when`(wishlistDao.isFavorite(any(), any())).thenReturn(correct)
        val response = dataSource.isFavorite(any(), any())
        assertEquals(response, correct)
    }

    @Test
    fun addFavorite() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(wishlistDao.addFavorite(favoriteTicket = any())).thenReturn(correct)
        val response = dataSource.addFavorite(favoriteTicket = any())
        assertEquals(response, correct)
    }

    @Test
    fun deleteFavorite() = runBlocking {
        val correct = mockk<Unit>()
        Mockito.`when`(wishlistDao.deleteFavorite(any(), any())).thenReturn(correct)
        val response = dataSource.deleteFavorite(any(), any())
        assertEquals(response, correct)
    }
}