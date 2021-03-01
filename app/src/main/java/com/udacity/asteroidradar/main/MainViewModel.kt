package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {
    private val _asteroids = MutableLiveData<List<Asteroid>>(listOf(
        Asteroid(1, "Testeroid", "1234-21-12", 1.0, 2.0, 3.0, 100.0, true),
        Asteroid(2, "Testeroid 2", "1234-21-13", 2.0, 2.0, 3.0, 100.0, true),
        Asteroid(3, "Testeroid 3", "1234-21-14", 3.0, 2.0, 3.0, 100.0, true),
    ))
//    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    private val _shouldNavigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val shouldNavigateToAsteroidDetails: LiveData<Asteroid>
        get() = _shouldNavigateToAsteroidDetails

    fun navigateToAsteroidDetails(asteroid: Asteroid) {
        _shouldNavigateToAsteroidDetails.value = asteroid
    }

    fun doneNavigatingToAsteroidDetails() {
        _shouldNavigateToAsteroidDetails.value = null
    }
}