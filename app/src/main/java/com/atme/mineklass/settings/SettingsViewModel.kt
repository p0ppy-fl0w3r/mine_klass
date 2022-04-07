package com.atme.mineklass.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.datastore.SettingPreferences
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var _refreshClassData = MutableLiveData<Boolean?>()

    val refreshClassData = Transformations.map(_refreshClassData) { it }

    private var _insertFromJson = MutableLiveData<Boolean?>()
    val insertFromJson: LiveData<Boolean?>
        get() = _insertFromJson

    private val database = UserClassDatabase.getInstance(getApplication())
    private val repository = ClassRepository(database)

    private val dataStore = SettingPreferences(application)

    val darkMode = dataStore.darkModeStatus.asLiveData()


    fun insertFromJson(classData: List<ClassData>) {
        viewModelScope.launch {
            _insertFromJson.value = true
            repository.deleteAll()
            repository.insertAll(classData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun doneRefresh() {
        _refreshClassData.value = null
    }

    fun doneInsert() {
        _insertFromJson.value = null
    }

    fun changeDarkStatus(value: Boolean?) {
        viewModelScope.launch {
            if (value == true) {
                dataStore.changeDarkMode(true)
            } else {
                dataStore.changeDarkMode(false)
            }
        }
    }

}