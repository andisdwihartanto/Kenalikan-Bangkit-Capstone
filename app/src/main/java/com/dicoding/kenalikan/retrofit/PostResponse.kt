package com.dicoding.kenalikan.retrofit

import com.google.gson.annotations.SerializedName

data class PostResponse(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String
)

