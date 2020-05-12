package com.devmmurray.flickrrocket.data.repository

import com.devmmurray.flickrrocket.data.api.FlickrApiService
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.API_KEY
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.SEARCH_METHOD
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.SORT
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.TAG_MODE
import com.devmmurray.flickrrocket.data.model.dto.GetPhotosDto
import retrofit2.Response

object ApiRepository {

    suspend fun searchPhotos(search: String): Response<GetPhotosDto> {
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
