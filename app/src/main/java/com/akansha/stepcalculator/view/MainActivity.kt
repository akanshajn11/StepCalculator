package com.akansha.stepcalculator.view

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.akansha.stepcalculator.R
import com.akansha.stepcalculator.viewmodel.FitnessViewModel
import com.akansha.stepcalculator.viewmodel.FitnessViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FitnessViewModel
    private var currentProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this, FitnessViewModelFactory(application)
        ).get(FitnessViewModel::class.java)

        setupObservers()

        progressBar.max = 1000

    }

    private fun setupObservers() {

        viewModel.fitnessData.observe(this, Observer { data ->
            sleep_count.text = data.sleepTime.plus(" HOURS")
            heart_count.text = data.heartRate
            training_count.text = data.trainingTime.toUpperCase()
        })

        viewModel.steps.observe(this, Observer { steps ->
            steps_count.text = steps.toString()
            currentProgress = steps
            ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
                .setDuration(500)
                .start()
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

}