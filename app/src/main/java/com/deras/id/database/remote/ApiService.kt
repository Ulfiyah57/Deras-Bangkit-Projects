package com.deras.id.database.remote

import com.deras.id.response.ResponseDetection
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response<ResponseDetection>

    @GET("predict/{id}")
    suspend fun getDetectionResult(
        @Path("id") id: String
    ): Response<ResponseDetection>
}

data class PredictionResponse(
    val data: PredictionData?
)

data class PredictionData(
    val result: String?,
    val createdAt: String?,
    val suggestion: String?,
    val explanation: String?
)
