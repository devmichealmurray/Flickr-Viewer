package com.devmmurray.flickrrocket.data.database

import com.devmmurray.flickrrocket.data.model.domain.PhotoObject

interface IFavoritesDataSource {

    suspend fun addFavorite(favorite: PhotoObject)
    suspend fun getFavorite(id: Long): PhotoObject?
    suspend fun getAllFavorites(): List<PhotoObject>
    suspend fun removeFavorite(favorite: PhotoObject)
}