package com.atme.mineklass.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserClassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(allData: List<UserClassData>)

    @Query("SELECT * FROM schedule_table")
    fun selectAll(): LiveData<List<UserClassData>>

    @Query("SELECT * FROM schedule_table WHERE id=:id")
    suspend fun getSchedule(id: String):UserClassData

    @Query("SELECT * FROM schedule_table WHERE day=:day")
    suspend fun getDaySchedule(day: String):List<UserClassData>

    @Query("SELECT count(*) FROM schedule_table")
    suspend fun getItemCount():Int

    @Query("DELETE FROM schedule_table")
    suspend fun deleteAll()

}