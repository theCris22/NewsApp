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

    suspend fun getBreakingNewsFromApi(): Pair<List<Article>?, String> {
        val response = api.getBreakingNews()

        return if (response.isSuccessful && response.body() != null && response.body()?.articles?.any() == true)
            Pair(response.body()!!.articles, "SUCCESS")
        else Pair(null, response.toString())

    }

    suspend fun searchNewsToApi(query: String): Pair<List<Article>?, String> {
        val response = api.searchNews(query)

        return if (response.isSuccessful && response.body() != null && response.body()?.articles?.any() == true)
            Pair(response.body()!!.articles, "SUCCESS")
        else Pair(null, response.toString())
    }

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