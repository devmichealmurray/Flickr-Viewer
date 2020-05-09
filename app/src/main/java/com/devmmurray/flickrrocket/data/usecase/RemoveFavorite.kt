package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.model.PhotoEntity
import com.devmmurray.flickrrocket.data.repository.DbRepository

class RemoveFavorite(private val repo: DbRepository) {
    suspend operator fun invoke(favorite: PhotoEntity) =
        repo.removeFavorite(favorite)
}