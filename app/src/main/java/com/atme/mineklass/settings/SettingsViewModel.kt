package com.atme.mineklass.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var _refreshClassData = MutableLiveData<Boolean?>()

    val refreshClassData = Transformations.map(_refreshClassData) { it }

    fun refreshClassData() {
        viewModelScope.launch {

                val database = UserClassDatabase.getInstance(getApplication())
                val repository = ClassRepository(database)
                repository.updateDatabase()


        }
    }

    fun startRefresh() {
        _refreshClassData.value = true
    }

    fun doneRefresh() {
        _refreshClassData.value = null
    }

}