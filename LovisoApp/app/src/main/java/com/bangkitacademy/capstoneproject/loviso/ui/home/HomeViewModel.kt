package com.bangkitacademy.capstoneproject.loviso.ui.home

import androidx.lifecycle.ViewModel
import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponseItem
import com.bangkitacademy.capstoneproject.loviso.data.retrofit.ApiConfig

class HomeViewModel : ViewModel() {
    companion object{

        val apiService = ApiConfig.getApiService()
        lateinit var predictsItems: List<RecommendationResponseItem>
        lateinit var recommendationsItems: List<RecommendationResponseItem>

    }

}