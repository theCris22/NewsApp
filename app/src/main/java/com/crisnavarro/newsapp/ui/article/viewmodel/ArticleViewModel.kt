package com.crisnavarro.newsapp.ui.article.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.network.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.savedNewToDb(article)
    }

}