package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.PictureOfDayRepository
import kotlinx.coroutines.launch


enum class AsteroidSelection { NEXT_WEEK, TODAY, ALL }


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)
    private val pictureRepository = PictureOfDayRepository()

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
        viewModelScope.launch {
            val pictureOfDay = pictureRepository.getPictureOfDay()
            println("Fetched picture of the day $pictureOfDay")
            _pictureOfDay.value = pictureOfDay
        }
    }

    val asteroids = repository.asteroids
    val _filter = MutableLiveData<AsteroidSelection>(AsteroidSelection.ALL)
    val filter: LiveData<AsteroidSelection>
        get() = _filter

    fun setFilter(asteroidSelection: AsteroidSelection) {
        _filter.value = asteroidSelection
    }

    val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    private val _shouldNavigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val shouldNavigateToAsteroidDetails: LiveData<Asteroid>
        get() = _shouldNavigateToAsteroidDetails

    fun navigateToAsteroidDetails(asteroid: Asteroid) {
        _shouldNavigateToAsteroidDetails.value = asteroid
    }

    fun doneNavigatingToAsteroidDetails() {
        _shouldNavigateToAsteroidDetails.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}