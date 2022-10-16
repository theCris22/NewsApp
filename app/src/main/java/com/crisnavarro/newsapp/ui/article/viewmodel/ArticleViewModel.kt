package com.crisnavarro.newsapp.ui.article.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.models.Article
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    fun saveArticle(context: Context, article: Article) = viewModelScope.launch {
        NewsRepository().savedNews(context, article)
    }

}