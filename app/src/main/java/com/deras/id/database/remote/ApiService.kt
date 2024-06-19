package com.deras.id.database.remote

import com.deras.id.response.ResponseDetection
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("Post data")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<ResponseDetection>

    @GET("detection/{id}")
    fun getDetectionResult(
        @Path("id") id: String
    ): Call<ResponseDetection>
}