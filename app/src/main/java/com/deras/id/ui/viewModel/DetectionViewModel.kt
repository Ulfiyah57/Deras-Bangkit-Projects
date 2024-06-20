package com.deras.id.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deras.id.database.remote.ApiConfig
import com.deras.id.database.repo.DetectionRepository
import com.deras.id.response.ResponseDetection
import kotlinx.coroutines.launch
import retrofit2.Response

class DetectionViewModel : ViewModel() {

    private val repository: DetectionRepository = DetectionRepository(ApiConfig.getApiService())

    private val _uploadResult = MutableLiveData<Response<ResponseDetection>>()
    val uploadResult: LiveData<Response<ResponseDetection>> get() = _uploadResult

    private val _detectionResult = MutableLiveData<Response<ResponseDetection>>()
    val detectionResult: LiveData<Response<ResponseDetection>> get() = _detectionResult

    val createdAt = MutableLiveData<String>()
    val suggestion = MutableLiveData<String>()
    val explanation = MutableLiveData<String>()

    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(fileUri)
                _uploadResult.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDetectionResult(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDetectionResult(id)
                _detectionResult.postValue(response)
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    data?.let {
                        createdAt.postValue(it.createdAt ?: "Created at not available")
                        suggestion.postValue(it.suggestion ?: "Suggestion not available")
                        explanation.postValue(it.explanation ?: "Explanation not available")
                    }
                } else {
                    createdAt.postValue("Created at not available")
                    suggestion.postValue("Suggestion not available")
                    explanation.postValue("Explanation not available")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}