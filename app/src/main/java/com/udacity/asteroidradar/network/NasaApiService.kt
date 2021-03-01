package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = Constants.BASE_URL


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()



interface NasaApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(@Query("api_key") token: String): Deferred<String>

    @GET("planetary/apod")
    fun getImageOfTheDay(@Query("api_key") token: String): Deferred<String>
}


object NasaApi {
    val asteroidService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}