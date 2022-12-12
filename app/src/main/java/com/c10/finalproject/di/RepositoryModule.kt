package com.c10.finalproject.di

import com.c10.finalproject.data.repository.AuthRepository
import com.c10.finalproject.data.repository.AuthRepositoryImpl
import com.c10.finalproject.data.repository.UserRepositoryImpl
import com.c10.finalproject.data.repository.UserRespository
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
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRespository
}