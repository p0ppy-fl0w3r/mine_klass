package com.atme.mineklass.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.classData.asUserClassData
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.database.asClassData
import com.atme.mineklass.network.ClassNetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class ClassRepository(private val database: UserClassDatabase) {
    val scheduleData: LiveData<List<ClassData>> =
        Transformations.map(database.scheduleDao.selectAll()) {
            it.asClassData()
        }

    suspend fun insertClass(classData: ClassData){
        database.scheduleDao.insertClass(classData.toUserClassData())
    }

    suspend fun updateDatabase() {
        val classNetworkData =
            ClassNetworkApi.classNetworkApi.getClassData().asUserClassData()
        database.scheduleDao.deleteAll()
        database.scheduleDao.insertAll(classNetworkData)
    }

    suspend fun getDaySchedule(day: String): List<UserClassData>{
        return database.scheduleDao.getDaySchedule(day)
    }

    suspend fun insertAll(classData: List<ClassData>){

        val mUserClassData = classData.asUserClassData()

        database.scheduleDao.insertAll(mUserClassData)
    }

    suspend fun getItemCount(): Int{
        return database.scheduleDao.getItemCount()
    }

    suspend fun getClass(id: String) : ClassData{
        return database.scheduleDao.getSchedule(id).asClassData()
    }

    suspend fun deleteAll(){
        database.scheduleDao.deleteAll()
    }
}
