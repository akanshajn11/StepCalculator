package com.example.stepcalculator.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stepcalculator.data.network.FitnessApi
import com.example.stepcalculator.data.db.FitnessDatabase
import com.example.stepcalculator.data.network.response.FitnessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessViewModel(val application: Application) : ViewModel() {

    val appDb: FitnessDatabase = FitnessDatabase.getInstance(application.applicationContext)

    private val _data = MutableLiveData<FitnessResponse>()

    val data: LiveData<FitnessResponse>
        get() = _data

    fun getFitnessData() {

        FitnessApi.fitnessService.getFitnessData().enqueue(object : Callback<FitnessResponse> {
            override fun onResponse(
                call: Call<FitnessResponse>,
                response: Response<FitnessResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _data.value = response.body()
                    appDb.fitnessDao.upsert(response.body()!!.fitnessData)
                }
            }

            override fun onFailure(call: Call<FitnessResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "Network failure!!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

    }
}