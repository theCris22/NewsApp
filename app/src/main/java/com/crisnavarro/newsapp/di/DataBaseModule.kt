package com.crisnavarro.newsapp.di

import android.content.Context
import androidx.room.Room
import com.crisnavarro.newsapp.data.db.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArticleDataBase::class.java, "article_db.db").build()

    @Singleton
    @Provides
    fun provideArticleDao(dataBase: ArticleDataBase) = dataBase.getArticleDao()

}