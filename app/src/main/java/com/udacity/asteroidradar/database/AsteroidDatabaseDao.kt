package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDatabaseDao {
    @Query("select * from asteroids ORDER BY close_approach_date DESC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroidEntities: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroidEntity: AsteroidEntity)

    @Query("delete from asteroids WHERE close_approach_date < :startDate")
    suspend fun deleteOldAsteroids(startDate: String): Unit

    // TODO add delete
}