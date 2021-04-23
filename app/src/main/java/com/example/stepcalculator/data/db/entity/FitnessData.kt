package com.example.stepcalculator.data.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val FITNESS_ID = 0

@Entity(tableName = "fitness_record")
data class FitnessData(

    @SerializedName("heart-rate")
    @ColumnInfo(name = "heart_rate")
    val heartRate: String,


    @SerializedName("sleep-time")
    @ColumnInfo(name = "sleep_time")
    val sleepTime: String,


    @SerializedName("training-time")
    @ColumnInfo(name = "training_time")
    val trainingTime: String
) {
    @PrimaryKey(autoGenerate = false)
    val id: Int = FITNESS_ID
}