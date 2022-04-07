package com.atme.mineklass.homePage


import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.atme.mineklass.ClassSlideWrapper
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.repository.ClassRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.sql.Wrapper
import java.util.Calendar

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

    private val second = 1000L

    private lateinit var timer: CountDownTimer

    private val _dayData = MutableLiveData<List<UserClassData>>()

    val dayData: LiveData<List<UserClassData>>
        get() = _dayData

    private val _allClassData = MutableLiveData<List<ClassSlideWrapper>>()

    val allClassData: LiveData<List<ClassSlideWrapper>>
        get() = _allClassData

    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private val _currentClassIndex = MutableLiveData<Int>()
    val currentClassIndex: LiveData<Int>
        get() = _currentClassIndex

    private val _currentClass = MutableLiveData<UserClassData>()
    val currentClass: LiveData<UserClassData>
        get() = _currentClass

    private val database = UserClassDatabase.getInstance(getApplication())
    private val repository = ClassRepository(database)


    init {
        updateDay()
    }

    fun getAllClassData(){
        viewModelScope.launch {
        val classItemList = mutableListOf<ClassSlideWrapper>()
            for (day in _mDays.values){
                val dayList = repository.getDaySchedule(day)
                classItemList.add(
                     ClassSlideWrapper(day = day, classes =  dayList)
                )
            }

            _allClassData.value = classItemList
        }
    }

    fun updateDay() {
        viewModelScope.launch {

            val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            // Kotlin hash map returns a nullable string(String?) instead of String.
            // All the values are accounted for. Trust me :)

            val weekDay: String = _mDays[currentDay]!!
            _dayData.value = repository.getDaySchedule(weekDay)
            Timber.i("Updated data from repository.")
        }
    }

    fun getFromInternet() {
        /* Get the data from the internet if the database is empty.
             This makes sure that the first page is not empty-
             when the app is started for the first time */
        viewModelScope.launch {

            Timber.e("Getting data from the internet")
            repository.updateDatabase()
            Timber.e("Got data from the internet.")
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
            if (::timer.isInitialized){
                // Make sure any previous timers are canceled.
                timer.cancel()
            }
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