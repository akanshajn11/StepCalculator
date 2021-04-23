package com.example.stepcalculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stepcalculator.data.db.entity.FitnessData

@Database(entities = [FitnessData::class], version = 1)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val fitnessDao: FitnessDao

    companion object {

        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getInstance(context: Context): FitnessDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FitnessDatabase::class.java,
                        "fitness_database"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }

        }
    }

}