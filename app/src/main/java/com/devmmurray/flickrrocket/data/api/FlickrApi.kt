package com.devmmurray.flickrrocket.data.api

import com.devmmurray.flickrrocket.data.model.Response
import retrofit2.Call
import retrofit2.http.GET

/**
 * Info needed once I have the project working.
 *  Api Key api_key=0e2b6aaf8a6901c264acb91f151a3350
 *
 *  Full Url =
 *  ?format=json&sort=random&method=flickr.photos.search&tags=rocket&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1.
 *  Base Url =
 *  https://api.flickr.com/services/rest/
 *  Open search =
 *  https://api.flickr.com/services/rest/?format=json&method=flickr.photos.getRecent&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1.
 */

private const val OPEN_SEARCH_URL =
    "?format=json&method=flickr.photos.getRecent&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."

interface FlickrApi {

    @GET(OPEN_SEARCH_URL)
    fun getPhotosUrlData(): Call<Response>

}