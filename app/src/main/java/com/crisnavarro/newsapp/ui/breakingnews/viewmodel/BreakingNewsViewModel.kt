package com.crisnavarro.newsapp.ui.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.responses.BreakingNewsResponse
import kotlinx.coroutines.launch

class BreakingNewsViewModel : ViewModel() {

    private val _news: MutableLiveData<BreakingNewsResponse> = MutableLiveData()
    val news: LiveData<BreakingNewsResponse> get() = _news

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        val call = NewsRepository().getBreakingNews()

        if (call.isSuccessful)
            call.body()?.let { _news.postValue(it) }

    }

}