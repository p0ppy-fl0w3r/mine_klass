package com.atme.mineklass.classDetails.editClassDetails

import android.app.Application
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import com.atme.mineklass.classData.ClassData

class EditClassDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ClassRepository(UserClassDatabase.getInstance(application))

    private val _insertToDatabase = MutableLiveData<Boolean?>()
    val insertToDatabase : LiveData<Boolean?>
        get() = _insertToDatabase

    fun saveClass(classData: ClassData){
        viewModelScope.launch {
            repository.insertClass(classData)
            _insertToDatabase.value = true
        }
    }

    fun doneInsert(){_insertToDatabase.value = null}
}