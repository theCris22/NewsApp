package com.crisnavarro.newsapp.ui.searchnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.responses.BreakingNewsResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private var _searchResult: MutableLiveData<BreakingNewsResponse> = MutableLiveData()
    val searchResult: LiveData<BreakingNewsResponse> get() = _searchResult

    fun searchNews(query: String) = viewModelScope.launch {
        val call = NewsRepository().searchNews(query)

        if (call.isSuccessful)
            call.body().let { _searchResult.postValue(it) }

    }

}