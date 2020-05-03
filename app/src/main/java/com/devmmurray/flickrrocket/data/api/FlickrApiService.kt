package com.devmmurray.flickrrocket.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.flickr.com/services/"


object FlickrApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiClient: FlickrApi = retrofit.create(FlickrApi::class.java)

}
