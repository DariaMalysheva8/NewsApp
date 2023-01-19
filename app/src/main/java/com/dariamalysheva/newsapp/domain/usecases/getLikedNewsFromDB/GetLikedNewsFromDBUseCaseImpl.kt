package com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB

import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.repository.NewsRepository

class GetLikedNewsFromDBUseCaseImpl(
    private val repository: NewsRepository
) : GetLikedNewsFromDBUseCase {

    override suspend operator fun invoke(): List<News> {

        return repository.getListOfLikedNewsFromDB()
    }
}