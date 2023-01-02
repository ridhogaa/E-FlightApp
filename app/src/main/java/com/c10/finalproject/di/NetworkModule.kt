package com.c10.finalproject.di

import com.c10.finalproject.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val baseUrl =
        "https://final-project-be-production-6de7.up.railway.app/" // Change ip address

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServiceAuth(retrofit: Retrofit): ApiServiceAuth =
        retrofit.create(ApiServiceAuth::class.java)

    @Singleton
    @Provides
    fun provideApiServiceUser(retrofit: Retrofit): ApiServiceUser =
        retrofit.create(ApiServiceUser::class.java)

    @Singleton
    @Provides
    fun provideApiServiceTicket(retrofit: Retrofit): ApiServiceTicket =
        retrofit.create(ApiServiceTicket::class.java)

    @Singleton
    @Provides
    fun provideApiServiceOrder(retrofit: Retrofit): ApiServiceOrder =
        retrofit.create(ApiServiceOrder::class.java)

    @Singleton
    @Provides
    fun provideApiServiceNotification(retrofit: Retrofit): ApiServiceNotification =
        retrofit.create(ApiServiceNotification::class.java)

}