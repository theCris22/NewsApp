package com.crisnavarro.newsapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.crisnavarro.newsapp.data.models.Article
import com.crisnavarro.newsapp.data.network.RetrofitInstance.api
import com.crisnavarro.newsapp.data.db.ArticleDataBase

class NewsRepository {

    suspend fun getBreakingNews() = api.getBreakingNews()
    suspend fun searchNews(query: String) = api.searchNews(query)

    suspend fun savedNews(context: Context, article: Article) {
        ArticleDataBase.getDatabase(context).getArticleDao().upsert(article)
    }

    fun getSavedNews (context: Context): LiveData<List<Article>> {
        return ArticleDataBase.getDatabase(context).getArticleDao().getAllNews()
    }

    suspend fun deleteNew (context: Context, article: Article) {
        ArticleDataBase.getDatabase(context).getArticleDao().deleteNew(article)
    }

}