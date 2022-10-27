package com.crisnavarro.newsapp.ui.searchnews.viewmodel

import android.util.Log
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
        when (call.second) {
            "SUCCESS" -> {
                _error.postValue(false)
                _searchResult.postValue(call.first!!)
            }
            else -> {
                _error.postValue(true)
                Log.e("ERROR ->", call.second)
            }

        }

        _loading.postValue(false)
    }

}