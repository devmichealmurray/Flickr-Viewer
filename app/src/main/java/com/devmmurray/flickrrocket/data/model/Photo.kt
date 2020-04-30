package com.devmmurray.flickrrocket.data.model

import com.google.gson.annotations.SerializedName

class Photo(
    val title: String,
    val link: String
)

class Response(
    @SerializedName("photos")
    var photos: Photos?
)

class Photos(
    @SerializedName("photo")
    var urlData: UrlData?
)

class UrlData(
    @SerializedName("id")
    var id: String,

    @SerializedName("secret")
    var secret: String,

    @SerializedName("server")
    var server: String,

    @SerializedName("farm")
    var farm: String,

    @SerializedName("title")
    var title: String
)