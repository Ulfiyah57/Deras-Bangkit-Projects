package com.deras.id.response

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("suggestion")
    val suggestion: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("explanation")
    val explanation: String? = null
)