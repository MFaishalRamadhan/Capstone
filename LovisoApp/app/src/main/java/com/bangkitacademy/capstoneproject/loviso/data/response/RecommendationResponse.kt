package com.bangkitacademy.capstoneproject.loviso.data.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("RecommendationResponse")
	val recommendationResponse: List<RecommendationResponseItem>
)

data class RecommendationResponseItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("distance")
	val distance: String,

	@field:SerializedName("num_ratings")
	val numRatings: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("url")
	val url: String,

	@SerializedName("user_latitude")
	val latitude: Double,

	@SerializedName("user_longitude")
	val longitude: Double
)

