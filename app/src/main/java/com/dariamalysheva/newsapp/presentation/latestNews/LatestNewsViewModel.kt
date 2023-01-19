package com.dariamalysheva.newsapp.presentation.latestNews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dariamalysheva.newsapp.data.NewsRepositoryImpl
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase.GetLatestNewsUseCase
import com.dariamalysheva.newsapp.domain.usecases.getLatestNewsUseCase.GetLatestNewsUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LatestNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepositoryImpl(application)

    private val getLatestNewsUseCase: GetLatestNewsUseCase = GetLatestNewsUseCaseImpl(repository)
    private val saveNewsToLikedDBUseCase: SaveNewsToLikedDBUseCase =
        SaveNewsToLikedDBUseCaseImpl(repository)
    private val deleteNewsFromLikedDBUseCase: DeleteNewsFromLikedDBUseCase =
        DeleteNewsFromLikedDBUseCaseImpl(repository)

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _listOfNews = MutableLiveData<List<News>>()
    val listOfNews: LiveData<List<News>>
        get() = _listOfNews

    fun getLatestNews(language: String?, region: String?, fromNetwork: Boolean?) {
        val latestNewsResponse = viewModelScope.async(Dispatchers.IO) {
            _loading.postValue(true)
            getLatestNewsUseCase(language, region, fromNetwork)
        }
        viewModelScope.launch {
            val latestNews = latestNewsResponse.await()
            _listOfNews.postValue(latestNews)
            _loading.postValue(false)
        }
    }

    fun refreshLatestNews(language: String?, region: String?, fromNetwork: Boolean?) {
        val latestNewsResponse = viewModelScope.async(Dispatchers.IO) {
            getLatestNewsUseCase(language, region, fromNetwork)
        }
        viewModelScope.launch {
            val latestNews = latestNewsResponse.await()
            _listOfNews.postValue(latestNews)
        }
    }

    fun saveNewsToLikeDb(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveNewsToLikedDBUseCase(id)
        }
    }

    fun deleteNewsToLikeDb(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNewsFromLikedDBUseCase(id)
        }
    }
}