package com.example.stepcalculator.data.network.response

import com.example.stepcalculator.data.db.entity.FitnessData
import com.google.gson.annotations.SerializedName


data class FitnessResponse(

    @SerializedName("data")
    var fitnessData: FitnessData

    )