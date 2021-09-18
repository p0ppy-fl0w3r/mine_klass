package com.atme.mineklass.splashScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import com.atme.mineklass.utils.JsonUtils
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    // TRIAL
    private val repository = ClassRepository(UserClassDatabase.getInstance(application))
    private val testClassDataJson =
        """[{"Day": "SUN", "Time": "07:00 AM - 09:00 AM", "Class Type": "Workshop", "Module Code": "CC6001NI", "Module Title ": "Advanced Database Systems Development", "Lecturer": "Mr. Lekhnath Katuwal", "Group": "C11", "Block": "Alumni", "Room": "SR10 - Sushma Shrestha", "id": 0}, {"Day": "MON", "Time": "07:00 AM - 09:00 AM", "Class Type": "Workshop", "Module Code": "CS6004NI", "Module Title ": "Application Development", "Lecturer": "Mr. Sushil Sapkota", "Group": "C11", "Block": "Nepal", "Room": "TR06 - Annapurna", "id": 1}, {"Day": "TUE", "Time": "07:00 AM - 09:00 AM", "Class Type": "Workshop", "Module Code": "CU6051NI", "Module Title ": "Artificial Intelligence", "Lecturer": "Mr. Bibek Khanal", "Group": "C11", "Block": "London", "Room": "SR03 - Piccadilly Circus", "id": 2}, {"Day": "WED", "Time": "08:00 AM - 09:00 AM", "Class Type": "Workshop", "Module Code": "FC6W51NI", "Module Title ": "Work Related Learning", "Lecturer": "Mr. Ravi Chandra Gurung", "Group": "C1+C7+C8+C11+C13", "Block": "Kumari", "Room": "Hall - 02", "id": 3}, {"Day": "THU", "Time": "10:00 AM - 11:30 AM", "Class Type": "Lecture", "Module Code": "CS6004NI", "Module Title ": "Application Development", "Lecturer": "Mr. Dhruba Sen", "Group": "C10+C11+C12+C13+C14+C15+C16+C17+C18", "Block": "Kumari", "Room": "Hall - 01", "id": 4}, {"Day": "FRI", "Time": "07:00 AM - 08:30 AM", "Class Type": "Lecture", "Module Code": "CC6001NI", "Module Title ": "Advanced Database Systems Development", "Lecturer": "Mr. Rohit Panday", "Group": "C10+C11+C12+C13+C14+C15+C16+C17+C18", "Block": "Kumari", "Room": "Hall - 01", "id": 5}, {"Day": "FRI", "Time": "08:30 AM - 10:00 AM", "Class Type": "Lecture", "Module Code": "CU6051NI", "Module Title ": "Artificial Intelligence", "Lecturer": "Mr. Sunil Raut Kshetry", "Group": "C10+C11+C12+C13+C14+C15+C16+C17+C18", "Block": "Kumari", "Room": "Hall - 01", "id": 6}]"""

    private val testClassData = JsonUtils.getClassFromJson(testClassDataJson)

    private val _startSavingTestData = MutableLiveData<Boolean?>()
    val startSavingTestData: LiveData<Boolean?>
        get() = _startSavingTestData

    fun insertData() {
        viewModelScope.launch {
            if (repository.getItemCount() < 1) {
                // To prevent replacing any data that was edited. All this will probably be removed later.
                repository.insertAll(testClassData!!)
            }
            _startSavingTestData.value = true
        }
    }

    fun doneInsert() {
        _startSavingTestData.value = null
    }

}