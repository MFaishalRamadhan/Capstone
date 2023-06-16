package com.bangkitacademy.capstoneproject.loviso.data.retrofit

import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponse
import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService{

    @FormUrlEncoded
    @POST("/locations")
    fun sendLocationToGetPredicts(
        @Field("user_latitude") latitude: Double,
        @Field("user_longitude") longitude: Double
    ): Call<ArrayList<RecommendationResponseItem>>

    @FormUrlEncoded
    @POST("/collaborative")
    fun sendLocationToGetCollaborative(
        @Field("user_latitude") latitude: Double,
        @Field("user_longitude") longitude: Double
    ): Call<ArrayList<RecommendationResponseItem>>

}