package com.insane.productshowcase.di.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOfflineInterceptor(@ApplicationContext context: Context): OfflineInterceptor {
        return OfflineInterceptor(context)
    }

    @Provides
    fun provideOnlineInterceptor(@ApplicationContext context: Context): OnlineInterceptor {
        return OnlineInterceptor(context)
    }
}