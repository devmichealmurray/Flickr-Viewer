package com.devmmurray.flickrrocket.data.database

import androidx.room.*
import com.devmmurray.flickrrocket.data.model.PhotoEntity

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(photo: PhotoEntity)

    @Update
    fun updateFavorite(photo: PhotoEntity)

    @Delete
    fun deleteFavorite(photo: PhotoEntity)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): MutableList<PhotoEntity>
}