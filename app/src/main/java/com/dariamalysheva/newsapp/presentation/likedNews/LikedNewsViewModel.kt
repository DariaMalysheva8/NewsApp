package com.dariamalysheva.newsapp.presentation.likedNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariamalysheva.newsapp.domain.entity.News
import com.dariamalysheva.newsapp.domain.usecases.deleteNewsFromLikedDb.DeleteNewsFromLikedDBUseCase
import com.dariamalysheva.newsapp.domain.usecases.getLikedNewsFromDB.GetLikedNewsFromDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class LikedNewsViewModel @Inject constructor(
    private val getLikedNewsFromDBUseCase: GetLikedNewsFromDBUseCase,
    private val deleteNewsFromLikedDBUseCase: DeleteNewsFromLikedDBUseCase,
) : ViewModel() {

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