package com.example.stepcalculator.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.stepcalculator.data.db.FitnessDao
import com.example.stepcalculator.data.network.FitnessApi
import com.example.stepcalculator.data.db.FitnessDatabase
import com.example.stepcalculator.data.db.entity.FitnessData
import com.example.stepcalculator.data.network.response.FitnessResponse
import com.example.stepcalculator.utilities.StepCounter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessViewModel(private val app: Application) : AndroidViewModel(app) {

    private val appDb: FitnessDao?

    private var _liveFitnessData = MutableLiveData<FitnessData>()

    val fitnessData: LiveData<FitnessData>
        get() = _liveFitnessData

    private var _fitnessDataDb = MutableLiveData<FitnessData>()

    val fitnessDataDb: LiveData<FitnessData>
        get() = _fitnessDataDb


    private val _formattedSteps = MutableLiveData<Int>()

    val formattedSteps: LiveData<Int>
        get() = _formattedSteps

    private val stepCounter: StepCounter = StepCounter(app.applicationContext)

    private var cachedSteps : Int =0

    init {
      cachedSteps=getSteps()

        stepCounter.steps.observeForever {
            this._formattedSteps.value = getSteps()+it
            _liveFitnessData.value?.steps = _formattedSteps.value ?: 0
        }
        appDb = FitnessDatabase.getInstance(app.applicationContext)?.fitnessDao

    }

    fun getFitnessData() {
        FitnessApi.shared.getFitnessData().enqueue(object : Callback<FitnessResponse> {
            override fun onResponse(
                call: Call<FitnessResponse>,
                response: Response<FitnessResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _liveFitnessData.value = response.body()?.fitnessData
                    //              _liveFitnessData.value?.fitnessData?.let { appDb?.upsert(it) }
                }
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

        _liveFitnessData.value?.steps= _formattedSteps.value ?:0
        //     fitnessData?.steps=_formattedSteps.value?:0
        if (fitnessData != null) {
            appDb?.upsert(fitnessData)
        }
    }

    fun getData() {

        //first check the cached data
        var fitnessData: FitnessData? = null
        fitnessData = appDb?.getFitnessData()
        // _liveFitnessData= appDb?.getFitnessData() as MutableLiveData<FitnessData>

        if (fitnessData == null) {

             getFitnessData()
        }
        else{
            _liveFitnessData.value=fitnessData
        }
    }

    private fun getSteps():Int{

       return appDb?.getSteps() ?: 0;

    }
}
