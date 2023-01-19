package com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb

interface DeleteNewsFromLikedDBUseCase {

    suspend operator fun invoke(id: String)
}