package com.dariamalysheva.newsapp.presentation.searchNews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dariamalysheva.newsapp.data.NewsRepositoryImpl
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions.GetSearchNewsUsingOptionsUseCase
import com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions.GetSearchSearchNewsUsingOptionsUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepositoryImpl(application)

    private val getSearchNewsUsingOptionsUseCase: GetSearchNewsUsingOptionsUseCase =
        GetSearchSearchNewsUsingOptionsUseCaseImpl(repository)
    private val saveNewsToLikedDBUseCase: SaveNewsToLikedDBUseCase =
        SaveNewsToLikedDBUseCaseImpl(repository)
    private val deleteNewsFromLikedDBUseCase: DeleteNewsFromLikedDBUseCase =
        DeleteNewsFromLikedDBUseCaseImpl(repository)

    private val _listOfNews = MutableLiveData<List<News>>()
    val listOfNews: LiveData<List<News>>
        get() = _listOfNews

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getSearchNewsUsingOptions(options: Map<String, String?>, fromNetwork: Boolean?) {
        val newsResponse = viewModelScope.async(Dispatchers.IO) {
            _loading.postValue(true)
            getSearchNewsUsingOptionsUseCase(options, fromNetwork)
        }
        viewModelScope.launch {
            _listOfNews.postValue(newsResponse.await())
            _loading.postValue(false)
        }
    }

    fun refreshSearchNewsUsingOptions(options: Map<String, String?>, fromNetwork: Boolean?) {
        val newsResponse = viewModelScope.async(Dispatchers.IO) {
            getSearchNewsUsingOptionsUseCase(options, fromNetwork)
        }
        viewModelScope.launch {
            _listOfNews.postValue(newsResponse.await())
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