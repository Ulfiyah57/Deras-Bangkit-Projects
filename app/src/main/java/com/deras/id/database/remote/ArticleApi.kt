package com.deras.id.database.remote

import com.deras.id.response.ResponseArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String = "health",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = "24e34f8f2eee4878993d7575ed1664ec"
    ): Response<ResponseArticle>
}
