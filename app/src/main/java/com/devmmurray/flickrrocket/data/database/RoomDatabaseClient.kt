package com.devmmurray.flickrrocket.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmmurray.flickrrocket.data.model.dto.PhotoEntity

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "favorites-db"

@Database(version = DATABASE_SCHEMA_VERSION, entities = [PhotoEntity::class])
abstract class RoomDatabaseClient : RoomDatabase() {

    abstract fun favoritesDAO(): FavoritesDAO

    companion object {
        private var instance: RoomDatabaseClient? = null

        private fun createDatabase(context: Context): RoomDatabaseClient {
            return Room
                .databaseBuilder(context, RoomDatabaseClient::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getDbInstance(context: Context): RoomDatabaseClient =
            (instance ?: createDatabase(context)).also { instance = it }

    }
}