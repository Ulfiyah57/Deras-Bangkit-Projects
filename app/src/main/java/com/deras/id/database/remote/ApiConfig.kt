package com.deras.id.database.remote

import com.deras.id.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private const val BASE_URL_PREDICTION = "http://34.101.142.29:5500/"
        private const val BASE_URL_ARTICLE = "https://newsapi.org/v2/"
        private const val API_KEY = "24e34f8f2eee4878993d7575ed1664ec" // Ganti dengan api_key Anda

        private fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private fun getLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        }

        private fun getOkHttpClientWithApiKey(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build()

                    val requestBuilder = original.newBuilder().url(url)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
        }

        private fun getDefaultOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .build()
        }

        fun getPredictionApiService(): ApiService {
            val retrofit = createRetrofit(BASE_URL_PREDICTION, getDefaultOkHttpClient())
            return retrofit.create(ApiService::class.java)
        }

        fun getArticleApiService(): ArticleApi {
            val retrofit = createRetrofit(BASE_URL_ARTICLE, getOkHttpClientWithApiKey())
            return retrofit.create(ArticleApi::class.java)
        }
    }
}