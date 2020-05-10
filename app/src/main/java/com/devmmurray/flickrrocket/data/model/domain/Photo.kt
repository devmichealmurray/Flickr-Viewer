package com.devmmurray.flickrrocket.data.model.domain


class PhotoObject(
    val title: String?,
    val link: String?,
    var isFavorite: Boolean = false,
    var comment: String? = "",
    var id: Long = 0L
)