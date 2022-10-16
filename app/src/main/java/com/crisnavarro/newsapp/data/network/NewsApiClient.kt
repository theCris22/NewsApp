package com.crisnavarro.newsapp.data.network

import com.crisnavarro.newsapp.core.Constants
import com.crisnavarro.newsapp.data.responses.BreakingNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiClient {

    @GET(Constants.Breaking_News_End_Point)
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "mx"
    ): Response<BreakingNewsResponse>

    @GET(Constants.Search)
    suspend fun searchNews(
        @Query("q") query: String
    ): Response<BreakingNewsResponse>

}