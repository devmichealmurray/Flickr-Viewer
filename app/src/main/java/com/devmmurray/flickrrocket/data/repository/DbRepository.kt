package com.devmmurray.flickrrocket.data.repository

import com.devmmurray.flickrrocket.data.model.PhotoEntity

class DbRepository(private val dataSource: IFavoritesDataSource) {

    suspend fun addFavorite(favorite: PhotoEntity)
            = dataSource.addFavorite(favorite)

    suspend fun getFavorite(id: Long): PhotoEntity?
            = dataSource.getFavorite(id)

    suspend fun updateFavorite(id: Long)
            = dataSource.updateFavorite(id)

    suspend fun getAllFavorites(): List<PhotoEntity>
            = dataSource.getAllFavorites()

    suspend fun removeFavorite(favorite: PhotoEntity)
            = dataSource.removeFavorite(favorite)
}