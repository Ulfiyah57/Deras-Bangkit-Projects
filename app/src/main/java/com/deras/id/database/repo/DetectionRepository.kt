package com.deras.id.database.repo

import android.net.Uri
import com.deras.id.database.remote.ApiService
import com.deras.id.response.ResponseDetection
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class DetectionRepository(private val apiService: ApiService) {

    suspend fun uploadImage(fileUri: Uri): Response<ResponseDetection> {
        return try {
            val file = File(fileUri.path!!)
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            apiService.uploadImage(body)
        } catch (e: Exception) {
            // Handle exceptions here (logging, rethrow, etc.)
            throw e
        }
    }

    suspend fun getDetectionResult(id: String): Response<ResponseDetection> {
        return try {
            apiService.getDetectionResult(id)
        } catch (e: Exception) {
            // Handle exceptions here (logging, rethrow, etc.)
            throw e
        }
    }
}