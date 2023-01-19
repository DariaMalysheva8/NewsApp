package com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase

import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.repository.NewsRepository

class GetLatestNewsUseCaseImpl(
    private val repository: NewsRepository
) : GetLatestNewsUseCase {

    override suspend fun invoke(
        language: String?,
        region: String?,
        fromNetwork: Boolean?
    ): List<News> {
        if (fromNetwork == true) {
            val listOfNewsFromNetwork = repository.getLatestNewsFromNetwork(language, region)
            if (listOfNewsFromNetwork.isNotEmpty()) {
                repository.deleteLatestNewsFromDB()
                listOfNewsFromNetwork.forEach { news ->
                    news.liked = repository.getLikedNewsByIdFromDB(news.id) != null
                }
                repository.saveLatestNewsFromNetworkToDB(listOfNewsFromNetwork)
            }

            return repository.getLatestNewsFromDB()
        } else {
            val latestNewsFromDb = repository.getLatestNewsFromDB()
            if (latestNewsFromDb.isNotEmpty()) {

                return latestNewsFromDb
            }
        }

        return listOf<News>()
    }
}