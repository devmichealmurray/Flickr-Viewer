package com.devmmurray.flickrrocket.data.repository

import com.devmmurray.flickrrocket.data.api.FlickrApiService
import com.devmmurray.flickrrocket.data.model.dto.GetPhotosDto
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.API_KEY
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.OPEN_SEARCH_METHOD
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.SEARCH_METHOD
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.SORT
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.TAG_MODE
import retrofit2.Response

object ApiRepository {

    suspend fun getRecentPhotosJson(): Response<GetPhotosDto> {
        return FlickrApiService.apiClient.getRecentPhotosUrlData(
            format = "json",
            method = OPEN_SEARCH_METHOD,
            apiKey = API_KEY,
            noJsonCallBack = "1"
        )
    }

    suspend fun searchPhotos(search: String):Response<GetPhotosDto> {
        return FlickrApiService.apiClient.searchPhotos(
            format = "json",
            method = SEARCH_METHOD,
            sort = SORT,
            tags = search,
            tagMode = TAG_MODE,
            apiKey = API_KEY,
            noJsonCallBack = "1"
        )
    }
}
