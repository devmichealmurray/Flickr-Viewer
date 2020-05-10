package com.devmmurray.flickrrocket.data.database

import androidx.room.*
import com.devmmurray.flickrrocket.data.model.dto.PhotoEntity

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(photo: PhotoEntity)

    @Query("SELECT * FROM favorites WHERE uid = :id")
    suspend fun getFavorite(id: Long): PhotoEntity

    @Delete
    suspend fun removeFavorite(photo: PhotoEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): MutableList<PhotoEntity>

}