package com.c10.finalproject.di

import android.content.Context
import com.c10.finalproject.data.local.database.AppDatabase
import com.c10.finalproject.data.local.database.dao.WishlistDao
import com.c10.finalproject.data.local.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getAppDB(context)

    @Singleton
    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao =
        database.wishlistDao()
}