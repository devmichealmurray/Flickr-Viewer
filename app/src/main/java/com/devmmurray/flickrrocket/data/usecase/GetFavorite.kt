package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.repository.DbRepository

class GetFavorite(private val repo: DbRepository) {
    suspend operator fun invoke(id: Long) =
        repo.getFavorite(id)
}