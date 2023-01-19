package com.dariamalysheva.newsapp.presentation.common.recyclerview

data class NewsVO(
    val id: String,
    val imageUrl: String,
    val title: String,
    val published: String,
    val description: String,
    val url: String,
    var liked: Boolean
)
