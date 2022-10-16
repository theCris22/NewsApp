package com.crisnavarro.newsapp.ui.savednews.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crisnavarro.newsapp.data.NewsRepository
import com.crisnavarro.newsapp.data.models.Article
import kotlinx.coroutines.launch

class SavedNewsViewModel : ViewModel() {

    fun getSavedNews(context: Context) = NewsRepository().getSavedNews(context)

    fun deleteNew(context: Context, article: Article) = viewModelScope.launch {
        NewsRepository().deleteNew(context, article)
    }

}