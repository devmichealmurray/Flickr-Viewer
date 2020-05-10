package com.devmmurray.flickrrocket.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPhotosDto(
    @Json(name = "photos") val photos: Photos?
)

@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "page") val page: Long?,
    @Json(name = "pages") val pages: Long?,
    @Json(name = "perpage") val perPage: Long?,
    @Json(name = "total") val total: Long?,
    @Json(name = "photo") val photoList: List<Photo>?
)

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "id") val id: String?,
    @Json(name = "owner") val owner: String?,
    @Json(name = "secret") val secret: String?,
    @Json(name = "server") val server: String?,
    @Json(name = "farm") val farm: Long?,
    @Json(name = "title") val title: String?,
    @Json(name = "ispublic") val isPublic: Long?,
    @Json(name = "isfriend") val isFriend: Long?,
    @Json(name = "isfamily") val isfamily: Long?
) {
    val urlLink: String? by lazy { buildUrl() }

    private fun buildUrl(): String? {
        return "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}_m.jpg"
    }
}