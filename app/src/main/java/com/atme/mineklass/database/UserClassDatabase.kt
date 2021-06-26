package com.atme.mineklass.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserClassData::class], version = 1, exportSchema = false)
abstract class UserClassDatabase : RoomDatabase() {
    abstract val scheduleDao: UserClassDao

    companion object {
        private var INSTANCE: UserClassDatabase? = null

        fun getInstance(context: Context): UserClassDatabase {
            synchronized(UserClassDatabase::class.java) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserClassDatabase::class.java,
                        "scheduledatabase"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }




}