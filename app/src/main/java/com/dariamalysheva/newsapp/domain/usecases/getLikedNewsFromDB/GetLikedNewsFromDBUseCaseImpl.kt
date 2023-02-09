package com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB

import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetLikedNewsFromDBUseCaseImpl @Inject constructor(
    private val repository: NewsRepository
) : GetLikedNewsFromDBUseCase {

    override suspend operator fun invoke(): List<News> {

        return repository.getListOfLikedNewsFromDB()
    }
}