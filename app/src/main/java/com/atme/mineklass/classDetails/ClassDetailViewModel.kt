package com.atme.mineklass.classDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch


class ClassDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository = ClassRepository(UserClassDatabase.getInstance(application))

    fun deleteClass(classData: ClassData){
        viewModelScope.launch {
            repository.deleteClass(classData.toUserClassData())
        }
    }

}