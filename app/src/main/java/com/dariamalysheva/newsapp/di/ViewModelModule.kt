package com.dariamalysheva.newsapp.di

import androidx.lifecycle.ViewModel
import com.dariamalysheva.newsapp.presentation.latestNews.LatestNewsViewModel
import com.dariamalysheva.newsapp.presentation.likedNews.LikedNewsViewModel
import com.dariamalysheva.newsapp.presentation.searchNews.SearchNewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LatestNewsViewModel::class)
    fun bindLatestNewsViewModel(viewModel: LatestNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchNewsViewModel::class)
    fun bindSearchNewsViewModel(viewModel: SearchNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LikedNewsViewModel::class)
    fun bindLikedNewsViewModel(viewModel: LikedNewsViewModel): ViewModel
}