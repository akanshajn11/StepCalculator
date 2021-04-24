package com.example.stepcalculator.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stepcalculator.data.db.entity.FITNESS_ID
import com.example.stepcalculator.data.db.entity.FitnessData
import com.example.stepcalculator.data.network.response.FitnessResponse

@Dao
interface FitnessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(fitnessEntry: FitnessData)

    @Query("Select * from fitness_record where id= $FITNESS_ID")
    fun getFitnessData(): FitnessData?

    @Query("Select steps from fitness_record where id= $FITNESS_ID")
    fun getSteps() : Int
}