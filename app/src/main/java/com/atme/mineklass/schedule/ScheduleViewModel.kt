package com.atme.mineklass.schedule

import android.app.Application
import androidx.lifecycle.*
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.network.ClassNetworkApi
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO change stuff
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val database = UserClassDatabase.getInstance(application)

    private val _classLiveData = ClassRepository(database).scheduleData

    val classLiveData: LiveData<List<ClassData>>
        get() = _classLiveData

    private val _navigateToDetail = MutableLiveData<ClassData?>()

    val navigateToDetail: LiveData<ClassData?>
        get() = _navigateToDetail

    init {
        getClassData()
    }

    private fun getClassData() {
        viewModelScope.launch {
            try {
                //ClassRepository(database).reloadSchedule()
                Timber.d("getClassData was called.")
            } catch (e: Exception) {
                Timber.e("Failed: $e")

            }
        }
    }

    fun navigateToClassDetail(classData: ClassData){
        _navigateToDetail.value = classData
    }

    fun doneNavigatingToDetail(){
        _navigateToDetail.value = null
    }

}