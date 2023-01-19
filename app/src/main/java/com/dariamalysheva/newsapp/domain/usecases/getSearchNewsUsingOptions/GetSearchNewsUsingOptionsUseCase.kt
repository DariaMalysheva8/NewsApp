package com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions

import com.dariamalysheva.newsapp.domain.entity.News

interface GetSearchNewsUsingOptionsUseCase {

    suspend operator fun invoke(options: Map<String, String?>, fromNetwork: Boolean?): List<News>
}