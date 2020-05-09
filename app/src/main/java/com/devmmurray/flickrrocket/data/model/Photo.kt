package com.devmmurray.flickrrocket.data.model


class PhotoObject(
    val id: Int = 0,
    val title: String?,
    val link: String?,
    var isFavorite: Boolean = false,
    var comment: String? = ""
)