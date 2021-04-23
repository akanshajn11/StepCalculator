package com.example.stepcalculator.data


import com.google.gson.annotations.SerializedName

data class FitnessData(
    @SerializedName("heart-rate")
    val heartRate: String,
    @SerializedName("sleep-time")
    val sleepTime: String,
    @SerializedName("training-time")
    val trainingTime: String
)