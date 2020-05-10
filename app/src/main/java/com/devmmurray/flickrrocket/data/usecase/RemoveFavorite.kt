package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.data.repository.DbRepository

class RemoveFavorite(private val repo: DbRepository) {
    suspend operator fun invoke(favorite: PhotoObject) =
        repo.removeFavorite(favorite)
}