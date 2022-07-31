package com.code.weather.repository

import androidx.room.*

@Database(entities = [CityLocation::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(cityLocation: CityLocation)

    @Delete
    suspend fun deleteCity(cityLocation: CityLocation)

    @androidx.room.Query("SELECT * FROM City_Table order by cityName")
    suspend fun getAllCities(): List<CityLocation>
}

@Entity(tableName = "City_Table")
data class CityLocation (
    @PrimaryKey
    @ColumnInfo(name = "cityName") val cityName: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)
