package com.deras.id.database.remote

import com.deras.id.BuildConfig.API_KEY
import com.deras.id.response.ResponseArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String = "health",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("decrip") desc:String = "description",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ResponseArticle>

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") query: String = "health",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("decrip") desc:String = "description",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ResponseArticle>
}
