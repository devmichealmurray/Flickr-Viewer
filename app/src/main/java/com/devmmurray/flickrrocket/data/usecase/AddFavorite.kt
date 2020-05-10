package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.data.repository.DbRepository

class AddFavorite(private val repo: DbRepository) {
    suspend operator fun invoke(favorite: PhotoObject)
    = repo.addFavorite(favorite)
}