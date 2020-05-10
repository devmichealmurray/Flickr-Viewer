package com.devmmurray.flickrrocket.data.repository

import com.devmmurray.flickrrocket.data.model.domain.PhotoObject

class DbRepository(private val dataSource: IFavoritesDataSource) {

    suspend fun addFavorite(favorite: PhotoObject)
            = dataSource.addFavorite(favorite)

    suspend fun getFavorite(id: Long)
            = dataSource.getFavorite(id)

    suspend fun getAllFavorites()
            = dataSource.getAllFavorites()

    suspend fun removeFavorite(favorite: PhotoObject)
            = dataSource.removeFavorite(favorite)
}