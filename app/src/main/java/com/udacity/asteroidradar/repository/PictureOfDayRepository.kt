/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parsePictureOfTheDayResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.NasaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PictureOfDayRepository() {
    suspend fun getPictureOfDay() : PictureOfDay? {
        var pictureOfDay: PictureOfDay? = null
        withContext(Dispatchers.IO) {
            try {
                val pictureOfDayString = NasaApi.asteroidService.getPictureOfTheDay(Constants.API_TOKEN).await()
                println("Fetched picture of day string! $pictureOfDayString")
                pictureOfDay = parsePictureOfTheDayResult(pictureOfDayString)
            } catch (e: Exception) {
                println("Couldn't fetch picture of the day, error $e")
            }
            null
        }
        return pictureOfDay
    }
}