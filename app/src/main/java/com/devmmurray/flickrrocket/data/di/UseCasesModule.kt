package com.devmmurray.flickrrocket.data.di

import com.devmmurray.flickrrocket.data.database.UseCases
import com.devmmurray.flickrrocket.data.repository.DbRepository
import com.devmmurray.flickrrocket.data.usecase.AddFavorite
import com.devmmurray.flickrrocket.data.usecase.GetAllFavorites
import com.devmmurray.flickrrocket.data.usecase.GetFavorite
import com.devmmurray.flickrrocket.data.usecase.RemoveFavorite
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repo: DbRepository) = UseCases(
        AddFavorite(repo),
        GetAllFavorites(repo),
        GetFavorite(repo),
        RemoveFavorite(repo)
    )
}