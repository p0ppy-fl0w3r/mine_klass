package com.atme.mineklass.classDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.classData.ClassData


class ClassDetailViewModelFactory(val classData: ClassData): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClassDetailViewModel::class.java)){
            return ClassDetailViewModel(classData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}