package com.deras.id.data.api.retrofit

import com.deras.id.BuildConfig
import com.deras.id.data.api.ResponseArticle
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String = "cancer",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("decrip") desc:String = "description",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ResponseArticle
}