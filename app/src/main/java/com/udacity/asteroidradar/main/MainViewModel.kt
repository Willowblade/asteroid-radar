package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {
    private val _asteroids = MutableLiveData<List<Asteroid>>()
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