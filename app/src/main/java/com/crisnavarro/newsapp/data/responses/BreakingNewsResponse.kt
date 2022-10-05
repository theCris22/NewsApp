package com.crisnavarro.newsapp.data.responses

import com.crisnavarro.newsapp.data.models.Article


data class BreakingNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)