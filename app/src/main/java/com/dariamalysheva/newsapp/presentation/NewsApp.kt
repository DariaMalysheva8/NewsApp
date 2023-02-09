package com.dariamalysheva.newsapp.presentation

import android.app.Application
import com.dariamalysheva.newsapp.di.DaggerAppComponent

class NewsApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}