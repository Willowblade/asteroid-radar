package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

//    private val _asteroids = MutableLiveData<List<Asteroid>>(listOf(
//        Asteroid(1, "Testeroid", "1234-21-12", 1.0, 2.0, 3.0, 100.0, true),
//        Asteroid(2, "Testeroid 2", "1234-21-13", 2.0, 2.0, 3.0, 100.0, true),
//        Asteroid(3, "Testeroid 3", "1234-21-14", 3.0, 2.0, 3.0, 100.0, true),
//    ))
//    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids = repository.asteroids


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