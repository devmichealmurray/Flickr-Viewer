package com.devmmurray.flickrrocket.data.database

import com.devmmurray.flickrrocket.data.usecase.AddFavorite
import com.devmmurray.flickrrocket.data.usecase.GetAllFavorites
import com.devmmurray.flickrrocket.data.usecase.GetFavorite
import com.devmmurray.flickrrocket.data.usecase.RemoveFavorite

data class UseCases(
    val addFavorite: AddFavorite,
    val getAllFavorites: GetAllFavorites,
    val getFavorite: GetFavorite,
    val removeFavorite: RemoveFavorite
)