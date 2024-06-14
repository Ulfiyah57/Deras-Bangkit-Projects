package com.deras.id.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

class ResultViewModel: ViewModel(){
    private val _article = MutableLiveData<ResultState<List<ArticlesItem?>?>>()
    val article = _article

    init {
        getNews()
    }

    private fun getNews(){
        viewModelScope.launch {
            try {
                _article.value = ResultState.Loading
                val response = ApiConfig.getApiService().getNews()
                if(response.status == "ok") {
                    _article.value = ResultState.Success(response.articles)
                }
            }catch (e: HttpException){
                _article.value = ResultState.Error(e.message())
            }
        }
    }

}