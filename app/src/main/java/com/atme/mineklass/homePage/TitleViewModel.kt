package com.atme.mineklass.homePage


import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
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

    private val second = 1000L

    private lateinit var timer: CountDownTimer

    val dayData: LiveData<List<UserClassData>>
        get() = _dayData

    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private val _currentClassIndex = MutableLiveData<Int>()
    val currentClassIndex: LiveData<Int>
        get() = _currentClassIndex

    private val _currentClass = MutableLiveData<UserClassData>()
    val currentClass: LiveData<UserClassData>
        get() = _currentClass

    init {
        updateDay()
    }

    fun updateDay() {
        viewModelScope.launch {
            val database = UserClassDatabase.getInstance(getApplication())
            val repository = ClassRepository(database)
            val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            /* Get the data from the internet if the database is empty.
             This makes sure that the first page is not empty-
             when the app is started for the first time */

            repository.reloadSchedule()

            // Kotlin hash map returns a nullable string(String?) instead of String.
            // All the values are accounted for. Trust me :)

            val weekDay: String = _mDays[currentDay]!!
            _dayData.value = database.scheduleDao.getDaySchedule(weekDay)
        }
    }

    fun setClassTimer(min: Long) {
        startTimer(min)
    }

    fun updateClass() {
        if (!_dayData.value.isNullOrEmpty()) {
            _currentClass.value = _currentClassIndex.value?.let { _dayData.value?.get(it) }
        }
    }

    fun updateClassIndex(index: Int) {
        _currentClassIndex.value = index
    }

    private fun startTimer(mil: Long) {

        viewModelScope.launch {
            timer = object : CountDownTimer(mil, second) {

                override fun onTick(millisUntilFinished: Long) {
                    _remainingTime.value = mil - System.currentTimeMillis()
                    if (_remainingTime.value!! <= 0) {
                        cancelTimer()
                    }
                }

                override fun onFinish() {
                    cancelTimer()
                }

            }
            timer.start()
        }
    }

    private fun incrementIndex() {
        if (_currentClassIndex.value!! <= _dayData.value!!.size) {
            _currentClassIndex.value = _currentClassIndex.value?.plus(1)
        }
    }

    private fun cancelTimer() {
        timer.cancel()
        _remainingTime.value = 0

        incrementIndex()
    }
}