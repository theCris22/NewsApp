package com.crisnavarro.newsapp.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.network.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {


    private val _news: MutableLiveData<List<Article>> = MutableLiveData()
    val news: LiveData<List<Article>> get() = _news

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> get() = _error

    init {
        getBreakingNews()
    }

    fun getBreakingNews() = viewModelScope.launch {
        _loading.postValue(true)

        val call = repository.getBreakingNewsFromApi()

        when {
            call.isSuccessful -> {

                _error.postValue(false)

                call.body()?.let {
                    if (it.articles.any())
                        _news.postValue(call.body()?.articles ?: listOf())
                    else
                        _error.postValue(true)

                }
            }

            !call.isSuccessful -> {
                _error.postValue(true)
            }
        }

        _loading.postValue(false)
    }

}