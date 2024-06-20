package com.deras.id.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deras.id.database.remote.ApiConfig
import com.deras.id.response.Article
import com.deras.id.response.ResponseArticle
import kotlinx.coroutines.launch
import retrofit2.Response

class ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> get() = _articles

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getArticleApiService().getNews()
                if (response.isSuccessful) {
                    _articles.value = response.body()?.articles
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}
