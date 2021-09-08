package com.atme.mineklass.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import com.atme.mineklass.classData.ClassData
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


    fun refreshClassData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAll()
                repository.updateDatabase()
                _refreshClassData.postValue(true)
            } catch (e: Exception) {
                Timber.e("Could not refresh data ${e.message}")
                _refreshClassData.postValue(false)
            }
        }
    }

    fun insertFromJson(classData: List<ClassData>) {
        viewModelScope.launch {
            _insertFromJson.value = true
            repository.deleteAll()
            repository.insertAll(classData)
        }
    }

    fun doneRefresh() {
        _refreshClassData.value = null
    }

    fun doneInsert() {
        _insertFromJson.value = null
    }

}