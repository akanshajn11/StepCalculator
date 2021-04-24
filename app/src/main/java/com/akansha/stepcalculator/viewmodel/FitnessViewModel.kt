package com.akansha.stepcalculator.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.akansha.stepcalculator.database.FitnessDao
import com.akansha.stepcalculator.database.FitnessData
import com.akansha.stepcalculator.utilities.network.FitnessApi
import com.akansha.stepcalculator.database.FitnessDatabase
import com.akansha.stepcalculator.utilities.network.FitnessResponse
import com.akansha.stepcalculator.utilities.sensor.StepCounter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessViewModel(private val app: Application) : AndroidViewModel(app) {

    private val appDb: FitnessDao?

    private var _liveFitnessData = MutableLiveData<FitnessData>()

    val fitnessData: LiveData<FitnessData>
        get() = _liveFitnessData

    private var _steps = MutableLiveData<Int>()

    val steps: LiveData<Int>
        get() = _steps

    private val stepCounter: StepCounter = StepCounter(app.applicationContext)

    init {

        stepCounter.steps.observeForever {
            _steps.value = getSavedSteps() + it -1
            _liveFitnessData.value?.steps = _steps.value ?: 0
        }
        appDb = FitnessDatabase.getInstance(app.applicationContext)?.fitnessDao

    }

    private fun getFitnessData() {
        FitnessApi.shared.getFitnessData().enqueue(object : Callback<FitnessResponse> {
            override fun onResponse(
                call: Call<FitnessResponse>,
                response: Response<FitnessResponse>
            ) {
                _liveFitnessData.value = response.body()?.fitnessData
            }

            override fun onFailure(call: Call<FitnessResponse>, t: Throwable) {
                Toast.makeText(
                    app.applicationContext,
                    "Network failure!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun saveData() {

        val fitnessData: FitnessData? = _liveFitnessData.value
        if (fitnessData != null) {
            appDb?.upsert(fitnessData)
        }
    }

    fun getData() {

        //check for cached data
        _steps.value=getSavedSteps()
        val fitnessData: FitnessData? = appDb?.getFitnessData()
        if (fitnessData == null)
            getFitnessData()
        else
            _liveFitnessData.value = fitnessData
    }

    private fun getSavedSteps(): Int {

        return appDb?.getSavedSteps() ?: 0;

    }
}
