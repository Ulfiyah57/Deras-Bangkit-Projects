package com.deras.id.database.repo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deras.id.database.remote.ApiService
import com.deras.id.response.ResponseDetection
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetectionRepository(private val apiService: ApiService) {

    fun uploadImage(fileUri: Uri, description: String): LiveData<ResponseDetection> {
        val result = MutableLiveData<ResponseDetection>()

        val file = File(fileUri.path!!)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())

        val call = apiService.uploadImage(body, descriptionBody)
        call.enqueue(object : Callback<ResponseDetection> {
            override fun onResponse(
                call: Call<ResponseDetection>,
                response: Response<ResponseDetection>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    result.value = ResponseDetection(message = "Upload Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetection>, t: Throwable) {
                result.value = ResponseDetection(message = "Upload Error: ${t.message}")
            }
        })

        return result
    }

    fun getDetectionResult(id: String): LiveData<ResponseDetection> {
        val result = MutableLiveData<ResponseDetection>()

        val call = apiService.getDetectionResult(id)
        call.enqueue(object : Callback<ResponseDetection> {
            override fun onResponse(
                call: Call<ResponseDetection>,
                response: Response<ResponseDetection>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    result.value = ResponseDetection(message = "Get Result Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetection>, t: Throwable) {
                result.value = ResponseDetection(message = "Get Result Error: ${t.message}")
            }
        })

        return result
    }
}