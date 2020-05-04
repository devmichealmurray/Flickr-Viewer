package com.devmmurray.flickrrocket.data.api

import com.devmmurray.flickrrocket.data.model.GetPhotosDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("rest/")
    suspend fun getRecentPhotosUrlData(
        @Query("format") format: String,
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("nojsoncallback") noJsonCallBack: String
    ): Response<GetPhotosDto>

    @GET("rest/")
    suspend fun searchPhotos(
        @Query("format") format: String,
        @Query("method") method: String,
        @Query("sort") sort: String,
        @Query("tags") tags: String,
        @Query("tag_mode") tagMode: String,
        @Query("api_key") apiKey: String,
        @Query("nojsoncallback") noJsonCallBack: String
    ): Response<GetPhotosDto>

}