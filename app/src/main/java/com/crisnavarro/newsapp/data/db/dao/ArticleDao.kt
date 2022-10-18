package com.crisnavarro.newsapp.data.db.dao;

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query
import com.crisnavarro.newsapp.data.network.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun deleteNew(article: Article)

}
