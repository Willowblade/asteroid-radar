package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.AsteroidEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable


fun Asteroid.asDatabaseModel(): AsteroidEntity {
    return AsteroidEntity(
        id,
        codename,
        closeApproachDate,
        absoluteMagnitude,
        estimatedDiameter,
        relativeVelocity,
        distanceFromEarth,
        isPotentiallyHazardous
    )
}

fun List<Asteroid>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        it.asDatabaseModel()
    }.toTypedArray()
}