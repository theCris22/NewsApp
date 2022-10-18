package com.crisnavarro.newsapp.data

import androidx.lifecycle.LiveData
import com.crisnavarro.newsapp.data.db.dao.ArticleDao
import com.crisnavarro.newsapp.data.network.NewsApiClient
import com.crisnavarro.newsapp.data.network.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApiClient,
    private val articleDao: ArticleDao
) {

    suspend fun getBreakingNewsFromApi() = api.getBreakingNews()
    suspend fun searchNewsToApi(query: String) = api.searchNews(query)

    suspend fun savedNewToDb(article: Article) {
        articleDao.upsert(article)
    }

    fun getSavedNewsFromDb(): LiveData<List<Article>> {
        return articleDao.getAllNews()
    }

    suspend fun deleteNewFromDb(article: Article) {
        articleDao.deleteNew(article)
    }

}