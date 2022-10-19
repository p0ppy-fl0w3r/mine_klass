package com.atme.mineklass.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserClassData::class], version = 3, exportSchema = false)
abstract class UserClassDatabase : RoomDatabase() {
    abstract val scheduleDao: UserClassDao

    companion object {
        private lateinit var INSTANCE: UserClassDatabase

        fun getInstance(context: Context): UserClassDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    UserClassDatabase::class.java,
                    "schedule_database"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE

        }
    }


}