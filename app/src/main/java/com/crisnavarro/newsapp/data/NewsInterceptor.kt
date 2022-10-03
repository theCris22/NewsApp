package com.crisnavarro.newsapp.data

import com.crisnavarro.newsapp.core.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

object NewsInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", API_KEY)
            .build()
        return chain.proceed(request)
    }
}