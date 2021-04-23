package com.example.stepcalculator.data.response

import com.example.stepcalculator.data.response.FitnessData
import com.google.gson.annotations.SerializedName


data class FitnessResponse(
    val code: Int,
    @SerializedName("data")
    val fitnessData: FitnessData,
    val success: String
)