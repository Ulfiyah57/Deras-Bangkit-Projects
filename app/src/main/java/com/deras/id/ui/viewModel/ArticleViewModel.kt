package com.deras.id.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deras.id.database.remote.ApiConfig
import com.deras.id.response.ArticlesItem
import com.deras.id.ui.article.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<ResultState<List<ArticlesItem>>>()
    val articles: LiveData<ResultState<List<ArticlesItem>>> get() = _articles

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                _articles.value = ResultState.Loading
                val response = ApiConfig.getArticleApiService().getNews()
                if (response.isSuccessful) {
                    _articles.value = ResultState.Success(response.body()?.articles?.filterNotNull() ?: emptyList())
                } else {
                    _articles.value = ResultState.Error("Failed to fetch articles")
                }
            } catch (e: IOException) {
                _articles.value = ResultState.Error("Network error occurred")
            } catch (e: HttpException) {
                _articles.value = ResultState.Error("HTTP error occurred")
            } catch (e: Exception) {
                _articles.value = ResultState.Error("Unknown error occurred")
            }
        }
    }
    fun fetchAllNews() {
        viewModelScope.launch {
            try {
                _articles.value = ResultState.Loading
                val response = ApiConfig.getArticleApiService().getAllNews()
                if (response.isSuccessful) {
                    _articles.value = ResultState.Success(response.body()?.articles?.filterNotNull() ?: emptyList())
                } else {
                    _articles.value = ResultState.Error("Failed to fetch articles")
                }
            } catch (e: IOException) {
                _articles.value = ResultState.Error("Network error occurred")
            } catch (e: HttpException) {
                _articles.value = ResultState.Error("HTTP error occurred")
            } catch (e: Exception) {
                _articles.value = ResultState.Error("Unknown error occurred")
            }
        }
    }
}