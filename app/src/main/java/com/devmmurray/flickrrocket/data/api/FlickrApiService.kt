package com.devmmurray.flickrrocket.data.api

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

//    private val api = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//        .create(FlickrApi::class.java)
//
//    fun getOpenSearchJson(): Single<Response> {
//        return api.getPhotosUrlData()
//    }
}
