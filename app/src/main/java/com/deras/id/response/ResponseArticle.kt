package com.deras.id.response

import com.google.gson.annotations.SerializedName

data class ResponseArticle(
    @SerializedName("totalResults")
    val totalResults: Int? = null,

    @SerializedName("articles")
    val articles: List<Article>? = null,

    @SerializedName("status")
    val status: String? = null
)

data class Article(
    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("source")
    val source: Source? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("content")
    val content: String? = null
)

data class Source(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Any? = null
)
