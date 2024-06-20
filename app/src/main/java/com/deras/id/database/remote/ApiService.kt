package com.deras.id.database.remote

import com.deras.id.response.ResponseDetection
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ResponseDetection>

    @GET("detection/{id}")
    fun getDetectionResult(
        @Path("id") id: String
    ): Response<ResponseDetection>
}
