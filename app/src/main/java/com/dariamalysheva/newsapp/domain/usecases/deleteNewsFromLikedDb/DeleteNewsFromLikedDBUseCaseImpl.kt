package com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb

import com.dariamalysheva.newsapp.domain.repository.NewsRepository

class DeleteNewsFromLikedDBUseCaseImpl(
    private val repository: NewsRepository
) : DeleteNewsFromLikedDBUseCase {

    override suspend operator fun invoke(id: String) {
        repository.deleteNewsFromLikedDB(id)
    }
}