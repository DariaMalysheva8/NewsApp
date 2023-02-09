package com.dariamalysheva.newsapp.data.network.retrofit.clients

import com.dariamalysheva.newsapp.data.network.retrofit.services.NewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsClient {

    val apiService = Retrofit.Builder()
        .baseUrl("https://api.currentsapi.services/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)
}