package com.akansha.stepcalculator.utilities.network

import com.akansha.stepcalculator.database.FitnessData
import com.google.gson.annotations.SerializedName


data class FitnessResponse(

    @SerializedName("data")
    var fitnessData: FitnessData

)