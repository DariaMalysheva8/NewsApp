package com.dariamalysheva.newsapp.di

import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase.GetLatestNewsUseCase
import com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase.GetLatestNewsUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB.GetLikedNewsFromDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB.GetLikedNewsFromDBUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions.GetSearchNewsUsingOptionsUseCase
import com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions.GetSearchSearchNewsUsingOptionsUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindDeleteNewsFromLikedDBUseCase(
        impl: DeleteNewsFromLikedDBUseCaseImpl
    ): DeleteNewsFromLikedDBUseCase

    @Binds
    fun bindGetLatestNewsUseCase(
        impl: GetLatestNewsUseCaseImpl
    ): GetLatestNewsUseCase

    @Binds
    fun bindGetLikedNewsFromDBUseCase(
        impl: GetLikedNewsFromDBUseCaseImpl
    ): GetLikedNewsFromDBUseCase

    @Binds
    fun bindGetSearchNewsUsingOptionsUseCase(
        impl: GetSearchSearchNewsUsingOptionsUseCaseImpl
    ): GetSearchNewsUsingOptionsUseCase

    @Binds
    fun bindSaveNewsToLikedDBUseCase(
        impl: SaveNewsToLikedDBUseCaseImpl
    ): SaveNewsToLikedDBUseCase
}