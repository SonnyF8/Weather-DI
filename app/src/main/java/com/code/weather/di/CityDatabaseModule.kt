package com.code.weather.di

import android.content.Context
import androidx.room.*
import com.code.weather.repository.CityDao
import com.code.weather.repository.CityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CityDatabaseModule {

    @Provides
    @Singleton
    fun provideCityDatabase(
        @ApplicationContext appContext: Context
    ): CityDatabase = Room.databaseBuilder(
        appContext,
        CityDatabase::class.java,
        "city_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCityDao(database: CityDatabase): CityDao = database.cityDao()
}
