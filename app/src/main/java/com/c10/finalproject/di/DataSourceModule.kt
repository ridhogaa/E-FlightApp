package com.c10.finalproject.di

import com.c10.finalproject.data.local.datasource.WishlistLocalDataSource
import com.c10.finalproject.data.local.datasource.WishlistLocalDataSourceImpl
import com.c10.finalproject.data.remote.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideTicketDataSource(ticketRemoteDataSource: TicketRemoteDataSourceImpl): TicketRemoteDataSource

    @Binds
    abstract fun provideOrderDataSource(orderRemoteDataSource: OrderRemoteDataSourceImpl): OrderRemoteDataSource

    @Binds
    abstract fun provideUserDataSource(userRemoteDataSource: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun provideNotificationDataSource(notificationRemoteDataSource: NotificationRemoteDataSourceImpl): NotificationRemoteDataSource

    @Binds
    abstract fun provideWishlistLocalDataSource(wishlistLocalDataSource: WishlistLocalDataSourceImpl): WishlistLocalDataSource
}