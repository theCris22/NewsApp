package com.crisnavarro.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crisnavarro.newsapp.data.db.converters.Converters
import com.crisnavarro.newsapp.data.db.dao.ArticleDao
import com.crisnavarro.newsapp.data.network.models.Article
import dagger.Provides

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}