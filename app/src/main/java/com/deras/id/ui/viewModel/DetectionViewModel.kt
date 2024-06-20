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

    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(fileUri)
                _uploadResult.postValue(response)
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    fun getDetectionResult(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDetectionResult(id)
                _detectionResult.postValue(response)
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }
}
