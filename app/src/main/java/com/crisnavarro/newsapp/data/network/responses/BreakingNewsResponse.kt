package com.crisnavarro.newsapp.data.network.responses

import com.crisnavarro.newsapp.data.network.models.Article


data class BreakingNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)