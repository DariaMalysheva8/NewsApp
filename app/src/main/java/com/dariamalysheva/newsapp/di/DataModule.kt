package com.dariamalysheva.newsapp.di

import android.app.Application
import com.dariamalysheva.newsapp.data.NewsRepositoryImpl
import com.dariamalysheva.newsapp.data.database.room.dao.NewsDao
import com.dariamalysheva.newsapp.data.database.room.database.AppDatabase
import com.dariamalysheva.newsapp.data.network.retrofit.clients.NewsClient
import com.dariamalysheva.newsapp.data.network.retrofit.services.NewsApiService
import com.dariamalysheva.newsapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideNewsDao(application: Application): NewsDao {
            return AppDatabase.getDatabase(application).getNewsDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): NewsApiService {
            return NewsClient.apiService
        }
    }
}