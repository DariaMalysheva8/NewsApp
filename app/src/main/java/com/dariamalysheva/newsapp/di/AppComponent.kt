package com.dariamalysheva.newsapp.di

import android.app.Application
import com.dariamalysheva.newsapp.presentation.latestNews.LatestNewsFragment
import com.dariamalysheva.newsapp.presentation.likedNews.LikedNewsFragment
import com.dariamalysheva.newsapp.presentation.searchNews.SearchNewsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: LatestNewsFragment)

    fun inject(fragment: SearchNewsFragment)

    fun inject(fragment: LikedNewsFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}