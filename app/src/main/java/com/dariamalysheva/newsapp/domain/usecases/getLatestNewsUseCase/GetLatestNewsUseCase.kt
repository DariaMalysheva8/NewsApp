package com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase

import com.dariamalysheva.newsapp.domain.entity.News

interface GetLatestNewsUseCase {

    suspend operator fun invoke(
        language: String?,
        region: String?,
        fromNetwork: Boolean?
    ): List<News>
}