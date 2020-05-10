package com.devmmurray.flickrrocket.data.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject

@Entity(tableName = "favorites")
data class PhotoEntity(
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "link")
    val link: String?,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "comments")
    var comment: String? = "",
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L
) {
    companion object {

        fun fromPhotoObject(photo: PhotoObject) =
            PhotoEntity(photo.title, photo.link, photo.isFavorite, photo.comment, photo.id)
    }

    fun toPhotoObject() =
        PhotoObject(title, link, isFavorite, comment, uid)
}