package com.devmmurray.flickrrocket.data.api

import com.devmmurray.flickrrocket.data.model.UrlData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.flickr.com/services/rest/"

class FlickrApiService {

    /**
     *  Here is my problem. I'm getting the Json, but I need to go a level down
     *  to pick up the array then, parse out the fields in that array
     *
     *  This is the url if you want to see the JSON
     *
     *  https://api.flickr.com/services/rest/?format=json&method=flickr.photos.getRecent&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1.
     *  
     */

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FlickrApi::class.java)

    fun getOpenSearchJson(): Single<ArrayList<UrlData>> {
        return api.getPhotosUrlData()
    }
}