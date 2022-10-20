package com.crisnavarro.newsapp.ui.searchnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.network.models.Article
import com.crisnavarro.newsapp.data.network.responses.BreakingNewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private var _searchResult: MutableLiveData<List<Article>> = MutableLiveData()
    val searchResult: LiveData<List<Article>> get() = _searchResult

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> get() = _error

    fun searchNews(query: String) = viewModelScope.launch {
        _loading.postValue(true)

        val call = repository.searchNewsToApi(query)
        when {
            call.isSuccessful -> {

                _error.postValue(false)

                call.body()?.let {
                    if (it.articles.any())
                        _searchResult.postValue(call.body()?.articles ?: listOf())
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