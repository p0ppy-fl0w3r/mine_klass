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
        """[{"Day": "SUN", "Time": "12:00 PM - 01:00 PM", "Class Type": "Workshop", "Module Code": "CS5002NI", "Module Title ": "Software Engineering", "Lecturer": "Mr. Rubin Thapa", "Group": "C11", "Block": "London", "Room": "SR04 - Tower of London", "id": "C11-0"}, {"Day": "MON", "Time": "07:00 AM - 09:00 AM", "Class Type": "Workshop", "Module Code": "CS5004NI", "Module Title ": "Emerging Programming Platforms & Tecnologies ", "Lecturer": "Mr. Shreyash Mool", "Group": "C11", "Block": "Nepal", "Room": "TR08 - Sagarmatha", "id": "C11-1"}, {"Day": "MON", "Time": "04:00 PM - 05:30 PM", "Class Type": "Discussion", "Module Code": "CS5002NI", "Module Title ": "Software Engineering", "Lecturer": "Mr. Rubin Thapa", "Group": "C1-C19", "Block": "UK", "Room": "TR12 - Gorkha", "id": "C11-2"}, {"Day": "MON", "Time": "05:30 PM - 07:00 PM", "Class Type": "Discussion", "Module Code": "CT5004NI", "Module Title ": "Professional Issues, Ethics and Computer Law", "Lecturer": "Mr. Aadesh Tandukar", "Group": "C1-C19+N1-N10", "Block": "UK", "Room": "TR12 - Gorkha", "id": "C11-3"}, {"Day": "TUE", "Time": "07:00 AM - 08:30 AM", "Class Type": "Lecture", "Module Code": "CS5004NI", "Module Title ": "Emerging Programming Platforms & Tecnologies ", "Lecturer": "Mr. Prithivi Maharjan", "Group": "C10+C11+C12", "Block": "London", "Room": "LT02 - Kensington Palace", "id": "C11-4"}, {"Day": "TUE", "Time": "08:30 AM - 10:00 AM", "Class Type": "Lecture", "Module Code": "CS5001NI", "Module Title ": "Networks and Operating Systems", "Lecturer": "Mr. Dipeshor Silwal", "Group": "C10+C11+C12", "Block": "London", "Room": "LT02 - Kensington Palace", "id": "C11-5"}, {"Day": "WED", "Time": "08:30 AM - 10:00 AM", "Class Type": "Lecture", "Module Code": "CS5002NI", "Module Title ": "Software Engineering", "Lecturer": "Mr. Rajesh Dware", "Group": "C10+C11+C12", "Block": "London", "Room": "LT02 - Kensington Palace", "id": "C11-6"}, {"Day": "WED", "Time": "11:30 AM - 01:00 PM", "Class Type": "Lecture", "Module Code": "CS5052NI", "Module Title ": "Professional Issues, Ethics and Computer Law", "Lecturer": "Ms. Yunisha Bajracharya", "Group": "C10+C11+C12", "Block": "London", "Room": "LT01 - Buckingham Palace", "id": "C11-7"}, {"Day": "WED", "Time": "04:00 PM - 05:30 PM", "Class Type": "Discussion", "Module Code": "CS5004NI", "Module Title ": "Emerging Programming Platforms & Tecnologies ", "Lecturer": "Mr. Shreyash Mool", "Group": "C1-C19", "Block": "UK", "Room": "TR08 - Sagarmatha", "id": "C11-8"}, {"Day": "THU", "Time": "01:00 PM - 03:00 PM", "Class Type": "Workshop", "Module Code": "CS5052NI", "Module Title ": "Professional Issues, Ethics and Computer Law", "Lecturer": "Ms. Suruchi Shrestha", "Group": "C11", "Block": "Nepal", "Room": "TR05 - Machapuchare", "id": "C11-9"}, {"Day": "THU", "Time": "04:00 PM - 05:30 PM", "Class Type": "Discussion", "Module Code": "CS5001NI", "Module Title ": "Network and Operating System", "Lecturer": "Mr. Dipeshor Silwal", "Group": "C1-C19", "Block": "UK", "Room": "TR12 - Gorkha", "id": "C11-10"}, {"Day": "FRI", "Time": "01:00 PM - 03:00 PM", "Class Type": "Workshop", "Module Code": "CS5001NI", "Module Title ": "Networks and Operating Systems", "Lecturer": "Mr. Umesh Nepal", "Group": "C11", "Block": "Alumni", "Room": "SR07 - Anish Manandhar", "id": "C11-11"}]"""

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