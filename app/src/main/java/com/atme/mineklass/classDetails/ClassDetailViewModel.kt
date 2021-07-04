package com.atme.mineklass.classDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.atme.mineklass.classData.ClassData

class ClassDetailViewModel(classData: ClassData) : ViewModel() {

    private val _liveClassData = MutableLiveData<ClassData>()

    val liveClassData: LiveData<ClassData>
        get() = _liveClassData

    val classLecturer = Transformations.map(liveClassData) {it ->
        it.lecturer
    }

    val classSubject = Transformations.map(liveClassData){
        it.module_title
    }

    val moduleType = Transformations.map(liveClassData){
        it.class_type
    }

    val moduleCode = Transformations.map(liveClassData){
        it.module_name
    }

    val id = Transformations.map(liveClassData){
        it.id
    }


    init {
        _liveClassData.value = classData
    }
}