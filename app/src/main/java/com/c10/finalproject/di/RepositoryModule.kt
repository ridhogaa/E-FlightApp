package com.c10.finalproject.di

import com.c10.finalproject.data.repository.*
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
abstract class RepositoryModule {
    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsTicketRepository(ticketRepositoryImpl: TicketRepositoryImpl): TicketRepository

    @Binds
    abstract fun bindsOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindsNotificationRepository(notificationRepository: NotificationRepositoryImpl): NotificationRepository

    @Binds
    abstract fun bindsWishlistRepository(wishlistRepository: WishlistRepositoryImpl): WishlistRepository
}