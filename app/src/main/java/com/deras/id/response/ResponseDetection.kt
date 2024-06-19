package com.deras.id.response

import com.google.gson.annotations.SerializedName

data class ResponseDetection(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
