package com.dariamalysheva.newsapp.data

import com.dariamalysheva.newsapp.common.utils.constants.Constants
import com.dariamalysheva.newsapp.data.database.room.dao.NewsDao
import com.dariamalysheva.newsapp.data.database.room.entities.toLikedNewsDB
import com.dariamalysheva.newsapp.data.database.room.entities.toNews
import com.dariamalysheva.newsapp.data.network.retrofit.models.toNews
import com.dariamalysheva.newsapp.data.network.retrofit.services.NewsApiService
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.entity.toLatestNewsDB
import com.dariamalysheva.newsapp.domain.entity.toSearchNewsDB
import com.dariamalysheva.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val apiService: NewsApiService
) : NewsRepository {

    override suspend fun getLatestNewsFromNetwork(language: String?, region: String?): List<News> {

        return try {
            apiService.getNews(
                language = language,
                country = region,
                apiKey = Constants.API_KEY
            ).news.map { latestNewsNetwork ->
                latestNewsNetwork.toNews()
            }
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun deleteLatestNewsFromDB() {
        newsDao.deleteAllLatestNews()
    }

    override suspend fun getLatestNewsByIdFromBD(id: String): News {

        return newsDao.getLatestNewsById(id).toNews()
    }

    override suspend fun getLatestNewsFromDB(): List<News> {

        return newsDao.getAllLatestNews().map { latestNewsDb ->
            latestNewsDb.toNews()
        }
    }

    override suspend fun saveLatestNewsFromNetworkToDB(listOfNews: List<News>) {
        val listOfLatestNewsDB = listOfNews.map { news ->
            news.toLatestNewsDB()
        }
        newsDao.insertAllToLatestNews(listOfLatestNewsDB)
    }

    override suspend fun saveNewsToLikedDB(id: String) {
        if (newsDao.existsInLatestNews(id)) {
            val latestNewsDB = newsDao.getLatestNewsById(id)
            latestNewsDB.liked = true
            newsDao.updateLatestNews(latestNewsDB)
            newsDao.insertOneNewsToLiked(latestNewsDB.toLikedNewsDB())
        }
        if (newsDao.existsInSearchNews(id)) {
            val searchNewsDB = newsDao.getSearchNewsById(id)
            searchNewsDB.liked = true
            newsDao.updateSearchNews(searchNewsDB)
            if (newsDao.getLikedNewsById(id) == null) {
                newsDao.insertOneNewsToLiked(searchNewsDB.toLikedNewsDB())
            }
        }
    }

    override suspend fun deleteNewsFromLikedDB(id: String) {
        if (newsDao.existsInLatestNews(id)) {
            val latestNewsDb = newsDao.getLatestNewsById(id)
            latestNewsDb.liked = false
            newsDao.updateLatestNews(latestNewsDb)
        }
        if (newsDao.existsInSearchNews(id)) {
            val searchNewsDb = newsDao.getSearchNewsById(id)
            searchNewsDb.liked = false
            newsDao.updateSearchNews(searchNewsDb)
        }

        newsDao.deleteNewsFromLikedById(id)
    }

    override suspend fun getLikedNewsByIdFromDB(id: String): News? {

        return newsDao.getLikedNewsById(id)?.toNews()
    }

    override suspend fun getListOfLikedNewsFromDB(): List<News> {

        return newsDao.getAllLikedNews().map { likedNewsDb ->
            likedNewsDb.toNews()
        }
    }

    override suspend fun getSearchNewsFromNetworkUsingOptions(
        options: Map<String, String?>
    ): List<News> {

        return try {
            apiService.searchNews(
                apiKey = Constants.API_KEY,
                options
            ).news.map { newsNetwork ->
                newsNetwork.toNews()
            }
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun deleteSearchNewsFromDB() {
        newsDao.deleteAllSearchNews()
    }

    override suspend fun getSearchNewsByIdFromBD(id: String): News {

        return newsDao.getSearchNewsById(id).toNews()
    }

    override suspend fun getSearchNewsFromDB(): List<News> {

        return newsDao.getAllSearchNews().map { searchNewsDB ->
            searchNewsDB.toNews()
        }
    }

    override suspend fun saveSearchNewsFromNetworkToDB(listOfNews: List<News>) {
        val listOfSearchNewsDB = listOfNews.map { news ->
            news.toSearchNewsDB()
        }
        newsDao.insertAllToSearchNews(listOfSearchNewsDB)
    }
}