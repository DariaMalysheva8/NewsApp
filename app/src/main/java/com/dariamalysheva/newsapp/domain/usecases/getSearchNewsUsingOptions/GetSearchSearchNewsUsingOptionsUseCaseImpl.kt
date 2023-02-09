package com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions

import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetSearchSearchNewsUsingOptionsUseCaseImpl @Inject constructor(
    private val repository: NewsRepository
) : GetSearchNewsUsingOptionsUseCase {

    override suspend operator fun invoke(
        options: Map<String, String?>,
        fromNetwork: Boolean?
    ): List<News> {
        if (fromNetwork == true) {
            val listOfNewsFromNetwork =
                repository.getSearchNewsFromNetworkUsingOptions(options)
            if (listOfNewsFromNetwork.isNotEmpty()) {
                repository.deleteSearchNewsFromDB()
                listOfNewsFromNetwork.forEach { news ->
                    news.liked = repository.getLikedNewsByIdFromDB(news.id) != null
                }
                repository.saveSearchNewsFromNetworkToDB(listOfNewsFromNetwork)
            }

            return repository.getSearchNewsFromDB()
        } else {
            val searchNewsFromDb = repository.getSearchNewsFromDB()
            if (searchNewsFromDb.isNotEmpty()) {

                return searchNewsFromDb
            }
        }

        return listOf<News>()
    }
}