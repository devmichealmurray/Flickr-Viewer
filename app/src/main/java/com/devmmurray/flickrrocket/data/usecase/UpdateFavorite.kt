package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.repository.DbRepository

class UpdateFavorite(private val repo: DbRepository) {
    suspend operator fun invoke(id: Long) = repo.updateFavorite(id)
}