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

class DetectionViewModel : ViewModel() {

    private val repository: DetectionRepository = DetectionRepository(ApiConfig.getApiService())

    private val _uploadResult = MutableLiveData<ResponseDetection>()
    val uploadResult: LiveData<ResponseDetection> get() = _uploadResult

    fun uploadImage(fileUri: Uri, description: String) {
        viewModelScope.launch {
            val result = repository.uploadImage(fileUri, description)
            _uploadResult.postValue(result.value)
        }
    }

    fun getDetectionResult(id: String): LiveData<ResponseDetection> {
        return repository.getDetectionResult(id)
    }
}