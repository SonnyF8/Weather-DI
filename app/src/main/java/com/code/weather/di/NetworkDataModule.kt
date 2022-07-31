package com.code.weather.di

import com.code.weather.NetworkData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkDataModule {

    @Provides
    @Singleton
    fun provideNetworkData(): NetworkData = NetworkData()
}
