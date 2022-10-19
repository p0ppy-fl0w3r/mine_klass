package com.atme.mineklass.classDetails.editClassDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class EditClassDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ClassRepository(UserClassDatabase.getInstance(application))

    private val _insertToDatabase = MutableLiveData<Boolean?>()
    val insertToDatabase: LiveData<Boolean?>
        get() = _insertToDatabase

    private val _currentClass = MutableLiveData<ClassData?>()
    val currentClass: LiveData<ClassData?>
        get() = _currentClass

    fun saveClass(classData: ClassData) {
        viewModelScope.launch {
            repository.insertClass(classData)
            _insertToDatabase.value = true
        }
    }

    fun addNewClass(classData: ClassData) {
        viewModelScope.launch {
            repository.insertClass(classData)
            _insertToDatabase.value = true
        }
    }

    fun startPopulateFields(id: Int) {
        viewModelScope.launch {
            _currentClass.value = repository.getClass(id)
        }
    }

    fun doneInsert() {
        _insertToDatabase.value = null
        _currentClass.value = null
    }
}