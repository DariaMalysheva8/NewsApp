package com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB

interface SaveNewsToLikedDBUseCase {

    suspend operator fun invoke(id: String)
}