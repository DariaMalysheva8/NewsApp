package com.dariamalysheva.newsapp.presentation.searchNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.getSearchNewsUsingOptions.GetSearchNewsUsingOptionsUseCase
import com.dariamalysheva.newsapp.domain.usecases.saveNewsToLikedDB.SaveNewsToLikedDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchNewsViewModel @Inject constructor(
    private val getSearchNewsUsingOptionsUseCase: GetSearchNewsUsingOptionsUseCase,
    private val saveNewsToLikedDBUseCase: SaveNewsToLikedDBUseCase,
    private val deleteNewsFromLikedDBUseCase: DeleteNewsFromLikedDBUseCase,
) : ViewModel() {

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