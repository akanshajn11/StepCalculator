package com.akansha.stepcalculator.utilities.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.pow
import kotlin.math.sqrt

class StepCounter(context: Context) : SensorEventListener {

    private val _steps = MutableLiveData<Int>().apply {
        value = 0
    }

    val steps: LiveData<Int>
        get() = _steps

    private var previousMagnitude: Float = 0.0F

    init {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun incrementStepsIfNeeded(accX: Float, accY: Float, accZ: Float) {
        val magnitude = sqrt((accX.pow(2) + accY.pow(2) + accZ.pow(2)))
        val magnitudeDelta = magnitude - this.previousMagnitude
        this.previousMagnitude = magnitude

        if (magnitudeDelta > 6) {
            this._steps.value?.let { steps ->
                this._steps.value = steps + 1
            }
        }
    }

    // ACCELEROMETER LISTENERS
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            this.incrementStepsIfNeeded(event.values[0], event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

