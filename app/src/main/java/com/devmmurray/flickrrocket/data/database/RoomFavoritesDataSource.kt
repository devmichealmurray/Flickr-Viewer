package com.devmmurray.flickrrocket.data.database

import android.content.Context
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.data.model.dto.PhotoEntity
import com.devmmurray.flickrrocket.data.repository.IFavoritesDataSource

class RoomFavoritesDataSource(context: Context) : IFavoritesDataSource {

    private val favoritesDAO =
        RoomDatabaseClient.getDbInstance(context).favoritesDAO()

    override suspend fun addFavorite(photo: PhotoObject) =
        favoritesDAO.addFavorite(PhotoEntity.fromPhotoObject(photo))

    override suspend fun getFavorite(id: Long) =
        favoritesDAO.getFavorite(id).toPhotoObject()

    override suspend fun getAllFavorites() =
        favoritesDAO.getAllFavorites().map { it.toPhotoObject() }

    override suspend fun removeFavorite(favorite: PhotoObject) =
        favoritesDAO.removeFavorite(PhotoEntity.fromPhotoObject(favorite))
}