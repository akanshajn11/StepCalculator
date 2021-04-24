package com.example.stepcalculator.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//A class that would know how to create Overview view model
class FitnessViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FitnessViewModel::class.java)) {
            return FitnessViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}