package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = Constants.BASE_URL




private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface NasaApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("api_key") token: String,
        @Query("start_date") start: String?,
        @Query("end_date") end: String?
    ): Deferred<String>

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") token: String): Deferred<String>
}


object NasaApi {
    val asteroidService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}