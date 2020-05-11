package com.devmmurray.flickrrocket.data.di

import android.app.Application
import com.devmmurray.flickrrocket.data.database.RoomFavoritesDataSource
import com.devmmurray.flickrrocket.data.repository.DbRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesRepository(app: Application)
            = DbRepository(RoomFavoritesDataSource(app))
}