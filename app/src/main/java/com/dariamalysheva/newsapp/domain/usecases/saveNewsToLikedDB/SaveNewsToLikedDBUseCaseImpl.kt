package com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB

import com.dariamalysheva.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class SaveNewsToLikedDBUseCaseImpl @Inject constructor(
    private val repository: NewsRepository
) : SaveNewsToLikedDBUseCase {

    override suspend operator fun invoke(id: String) {
        repository.saveNewsToLikedDB(id)
    }
}