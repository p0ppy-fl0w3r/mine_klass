package com.atme.mineklass.homePage


import android.app.Application
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class TitleViewModel(application: Application) : AndroidViewModel(application) {

    private val _mDays =
        hashMapOf(
            1 to "SUN",
            2 to "MON",
            3 to "TUE",
            4 to "WED",
            5 to "THU",
            6 to "FRI",
            7 to "SAT"
        )

    private val _dayData = MutableLiveData<List<UserClassData>>()

    val dayData: LiveData<List<UserClassData>>
        get() = _dayData


    init {
        updateDay()
    }



    private fun updateDay() {
        viewModelScope.launch {
            val database = UserClassDatabase.getInstance(getApplication())
            val repository = ClassRepository(database)
            val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            /* Get the data from the internet if the database is empty.
             This makes sure that the first page is not empty-
             when the app is started for the first time */

            repository.reloadSchedule()

            Timber.e("Update Day called")
            // Kotlin hash map returns a nullable string(String?) instead of String.
            // All the values are accounted for. Trust me :)

            val weekDay: String = _mDays[currentDay]!!
            _dayData.value = database.scheduleDao.getDaySchedule(weekDay)
        }
    }
}