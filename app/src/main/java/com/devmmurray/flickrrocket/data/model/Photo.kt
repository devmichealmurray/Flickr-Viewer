package com.devmmurray.flickrrocket.data.model

class PhotoObject(
    val title: String,
    val link: String,
    var isFavorite: Boolean = false,
    var comment: String = ""
) {
}