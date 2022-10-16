package com.crisnavarro.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crisnavarro.newsapp.data.db.converters.Converters
import com.crisnavarro.newsapp.data.db.dao.ArticleDao
import com.crisnavarro.newsapp.data.models.Article

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

    companion object {

        @Volatile
        private var instance: ArticleDataBase? = null

        fun getDatabase(context: Context): ArticleDataBase {
            return instance?: synchronized(this) {
                createDataBase(context)
            }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDataBase::class.java,
                "article_db.db"
            ).build()

    }

}