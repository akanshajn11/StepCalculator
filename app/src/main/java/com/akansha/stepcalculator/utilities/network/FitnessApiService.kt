package com.akansha.stepcalculator.utilities.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.jsonbin.io"

interface FitnessApiService {

    @GET("/b/60816ce39a9aa933335504a8")
    fun getFitnessData(): Call<FitnessResponse>
}

object FitnessApi {

    val shared: FitnessApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FitnessApiService::class.java)
    }
}