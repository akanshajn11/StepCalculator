package com.akansha.stepcalculator.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FitnessData::class], version = 1)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val fitnessDao: FitnessDao

    companion object {

        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getInstance(context: Context): FitnessDatabase? {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context,
                        FitnessDatabase::class.java,
                        "fitness_database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}