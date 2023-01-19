package com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB

import com.dariamalysheva.newsapp.domain.entity.News

interface GetLikedNewsFromDBUseCase {

    suspend operator fun invoke(): List<News>
}