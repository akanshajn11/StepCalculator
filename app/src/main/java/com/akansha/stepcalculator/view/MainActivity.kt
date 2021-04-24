package com.example.stepcalculator.ui

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.stepcalculator.R
import com.example.stepcalculator.data.db.entity.FitnessData
import com.example.stepcalculator.viewmodel.FitnessViewModel
import com.example.stepcalculator.viewmodel.FitnessViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FitnessViewModel

    private lateinit var textView: TextView

     private var  fitnessData : FitnessData?=null

    private lateinit var  gridView:GridView

private var totalSteps=0

    private var currentProgress = totalSteps


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //   textView = findViewById(R.id.steps)
        viewModel=ViewModelProvider(
            this, FitnessViewModelFactory(application))
            .get(FitnessViewModel::class.java)

      //  gridView=findViewById(R.id.grid)

     //   viewModel.getFitnessData()
        setupObservers()

        progressBar.max=1000

    }

    private fun setupObservers() {
        viewModel.fitnessData.observe(this, Observer { data ->
            Log.d("akan",data.toString())
         fitnessData =data
            sleep_view.text=data.sleepTime.plus(" HOURS")
            heart_view.text= data.heartRate
            time_view.text= data.trainingTime.toUpperCase()


        })

        viewModel.formattedSteps.observe(this, Observer { steps ->


            steps_view.text= steps.toString()
            totalSteps=steps

            currentProgress=totalSteps

            ObjectAnimator.ofInt(progressBar,"progress", currentProgress)
                .setDuration(500)
                .start()

        })


    }

    override fun onPause() {
        super.onPause()
        Log.d("anku", "onpauase")
        viewModel.saveData()
//        currentData.fitnessData.steps = stepCount
//        viewModel.saveData(applicationContext, currentData.fitnessData)

        // val test = viewModel.getDbData(applicationContext);
    }

//    override fun onRestart() {
//        super.onRestart()
//        viewModel.getData()
//    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

}