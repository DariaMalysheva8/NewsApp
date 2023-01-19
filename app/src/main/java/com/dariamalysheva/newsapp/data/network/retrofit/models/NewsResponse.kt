package com.dariamalysheva.newsapp.data.network.retrofit.models

data class NewsResponse(
    val news: List<NewsNetwork>,
    val page: Int,
    val status: String
)
