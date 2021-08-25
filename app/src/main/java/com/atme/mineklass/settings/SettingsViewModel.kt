package com.atme.mineklass.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import com.atme.mineklass.classData.ClassData


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var _refreshClassData = MutableLiveData<Boolean?>()

    val refreshClassData = Transformations.map(_refreshClassData) { it }

    private var _insertFromJson = MutableLiveData<Boolean?>()
    val insertFromJson: LiveData<Boolean?>
        get() = _insertFromJson

    private val database = UserClassDatabase.getInstance(getApplication())
    private val repository = ClassRepository(database)

    fun refreshClassData() {
        viewModelScope.launch {

            // If it ain't broke don't fix it!
            try {
                repository.deleteAll()
                repository.updateDatabase()
            } catch (e: retrofit2.HttpException) {
                Toast.makeText(getApplication(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun insertFromJson(classData: List<ClassData>) {
        viewModelScope.launch {
            repository.deleteAll()
            repository.insertAll(classData)
            doneInsert()
        }
    }

    fun startRefresh() {
        _refreshClassData.value = true
    }

    fun doneRefresh() {
        _refreshClassData.value = null
    }

    fun startInsert() {
        _insertFromJson.value = true
    }

    private fun doneInsert() {
        _insertFromJson.value = null
    }

}