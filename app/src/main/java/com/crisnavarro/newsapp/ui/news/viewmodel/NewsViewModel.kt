package com.crisnavarro.newsapp.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.network.responses.BreakingNewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {


    private val _news: MutableLiveData<BreakingNewsResponse> = MutableLiveData()
    val news: LiveData<BreakingNewsResponse> get() = _news

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    init {
        getBreakingNews()
    }

    fun getBreakingNews() = viewModelScope.launch {
        _loading.postValue(true)

        val call = repository.getBreakingNewsFromApi()
        if (call.isSuccessful)
            call.body()?.let { it -> _news.postValue(it) }

        _loading.postValue(false)
    }

}