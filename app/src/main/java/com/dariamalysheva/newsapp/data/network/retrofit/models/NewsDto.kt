package com.dariamalysheva.newsapp.data.network.retrofit.models

import com.dariamalysheva.newsapp.domain.entity.News

data class NewsNetwork(
    val author: String,
    val category: List<String>,
    val description: String,
    val id: String,
    val image: String,
    val language: String,
    val published: String,
    val title: String,
    val url: String
)

fun NewsNetwork.toNews(): News {

    return News(author, category, description, id, image, language, published, title, url)
}
