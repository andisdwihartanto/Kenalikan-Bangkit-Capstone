package com.dicoding.kenalikan.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String
)

