package com.devmmurray.flickrrocket.data.model

import com.squareup.moshi.Json

class Photo(
    val title: String,
    val link: String
)

class Response(
    @field:Json(name = "photos")
    val photos: Photos
)

class Photos(
    @field:Json(name = "photo")
    val photo: ArrayList<UrlData>
)

class UrlData(
    @field:Json(name = "id")
    var id: String,

    @field:Json(name = "secret")
    var secret: String,

    @field:Json(name = "server")
    var server: String,

    @field:Json(name = "farm")
    var farm: String,

    @field:Json(name = "title")
    var title: String
)