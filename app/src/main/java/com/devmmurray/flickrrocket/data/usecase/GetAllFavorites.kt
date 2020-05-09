package com.devmmurray.flickrrocket.data.usecase

import com.devmmurray.flickrrocket.data.repository.DbRepository

class GetAllFavorites(private val repo: DbRepository) {
    suspend operator fun invoke() = repo.getAllFavorites()
}