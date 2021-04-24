package com.akansha.stepcalculator.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FitnessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(fitnessEntry: FitnessData)

    @Query("Select * from fitness_record where id= $FITNESS_ID")
    fun getFitnessData(): FitnessData?

    @Query("Select steps from fitness_record where id= $FITNESS_ID")
    fun getSavedSteps(): Int

}