package com.dariamalysheva.newsapp.domain.entity

import com.dariamalysheva.newsapp.data.database.room.entities.LatestNewsDB
import com.dariamalysheva.newsapp.data.database.room.entities.SearchNewsDB
import com.dariamalysheva.newsapp.presentation.common.recyclerview.NewsVO

data class News(
    val author: String,
    val category: List<String>,
    val description: String,
    val id: String,
    val image: String,
    val language: String,
    val published: String,
    val title: String,
    val url: String,
    var liked: Boolean = false
)

fun News.toNewsVO(): NewsVO {

    return NewsVO(
        id = id, imageUrl = image, title = title, published = published,
        description = description, url = url, liked = liked
    )
}

fun News.toLatestNewsDB(): LatestNewsDB {

    return LatestNewsDB(
        id = id, author = author, category = category, description = description, image = image,
        language = language, published = published, title = title, url = url, liked = liked
    )
}

fun News.toSearchNewsDB(): SearchNewsDB {

    return SearchNewsDB(
        id = id, author = author, category = category, description = description, image = image,
        language = language, published = published, title = title, url = url, liked = liked
    )
}