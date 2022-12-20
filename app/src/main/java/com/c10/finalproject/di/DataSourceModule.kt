package com.c10.finalproject.di

import com.c10.finalproject.data.remote.tickets.datasource.OrderRemoteDataSource
import com.c10.finalproject.data.remote.tickets.datasource.OrderRemoteDataSourceImpl
import com.c10.finalproject.data.remote.tickets.datasource.TicketRemoteDataSource
import com.c10.finalproject.data.remote.tickets.datasource.TicketRemoteDataSourceImpl
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
}