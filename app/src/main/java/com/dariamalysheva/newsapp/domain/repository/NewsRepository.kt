package com.dariamalysheva.newsapp.domain.repository

import com.dariamalysheva.newsapp.domain.entity.News

interface NewsRepository {

    suspend fun getLatestNewsFromNetwork(language: String?, region: String?): List<News>

    suspend fun deleteLatestNewsFromDB()

    suspend fun getLatestNewsByIdFromBD(id: String): News

    suspend fun getLatestNewsFromDB(): List<News>

    suspend fun saveLatestNewsFromNetworkToDB(listOfNews: List<News>)

    suspend fun getLikedNewsByIdFromDB(id: String): News?

    suspend fun saveNewsToLikedDB(id: String)

    suspend fun getListOfLikedNewsFromDB(): List<News>

    suspend fun deleteNewsFromLikedDB(id: String)

    suspend fun getSearchNewsFromNetworkUsingOptions(options: Map<String, String?>): List<News>

    suspend fun deleteSearchNewsFromDB()

    suspend fun getSearchNewsByIdFromBD(id: String): News

    suspend fun getSearchNewsFromDB(): List<News>

    suspend fun saveSearchNewsFromNetworkToDB(listOfNews: List<News>)
}