package com.devmmurray.flickrrocket.data.repository

import com.devmmurray.flickrrocket.data.model.PhotoEntity

interface IFavoritesDataSource {

    suspend fun addFavorite(favorite: PhotoEntity)
    suspend fun getFavorite(id: Long): PhotoEntity?
    suspend fun updateFavorite(id: Long)
    suspend fun getAllFavorites(): List<PhotoEntity>
    suspend fun removeFavorite(favorite: PhotoEntity)
}