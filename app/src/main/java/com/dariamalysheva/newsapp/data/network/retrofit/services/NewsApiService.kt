package com.dariamalysheva.newsapp.data.network.retrofit.services

import com.dariamalysheva.newsapp.data.network.retrofit.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApiService {

    @GET("latest-news")
    suspend fun getNews(
        @Query("language") language: String?,
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String,
    ): NewsResponse

    @GET("search")
    suspend fun searchNews(
        @Query("apiKey") apiKey: String,
        @QueryMap options: Map<String, String?>
    ): NewsResponse
}