package com.dariamalysheva.newsapp.presentation.likedNews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dariamalysheva.newsapp.data.NewsRepositoryImpl
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCaseImpl
import com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB.GetLikedNewsFromDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB.GetLikedNewsFromDBUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LikedNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepositoryImpl(application)

    private val getLikedNewsFromDBUseCase: GetLikedNewsFromDBUseCase =
        GetLikedNewsFromDBUseCaseImpl(repository)
    private val deleteNewsFromLikedDBUseCase: DeleteNewsFromLikedDBUseCase =
        DeleteNewsFromLikedDBUseCaseImpl(repository)

    private var _listOfLikedNews = MutableLiveData<List<News>>()
    val listOfLikedNews: LiveData<List<News>>
        get() = _listOfLikedNews

    fun getLikedNewsFromDb() {
        val likedNewsResponse = viewModelScope.async(Dispatchers.IO) {
            getLikedNewsFromDBUseCase()
        }

        viewModelScope.launch {
            _listOfLikedNews.postValue(likedNewsResponse.await())
        }
    }

    fun deleteNewsToLikeDb(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNewsFromLikedDBUseCase(id)
            _listOfLikedNews.postValue(getLikedNewsFromDBUseCase())
        }
    }

}