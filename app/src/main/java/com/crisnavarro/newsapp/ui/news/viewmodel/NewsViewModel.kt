package com.crisnavarro.newsapp.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.responses.BreakingNewsResponse
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _news: MutableLiveData<BreakingNewsResponse> = MutableLiveData()
    val news: LiveData<BreakingNewsResponse> get() = _news

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        _loading.postValue(true)

        val call = NewsRepository().getBreakingNews()
        if (call.isSuccessful)
            call.body()?.let { _news.postValue(it) }

        _loading.postValue(false)
    }

}