package com.atme.mineklass.splashScreen

import android.app.Application
import androidx.lifecycle.*
import com.atme.mineklass.database.UserClassDatabase
import com.atme.mineklass.datastore.SettingPreferences
import com.atme.mineklass.repository.ClassRepository
import com.atme.mineklass.utils.JsonUtils
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ClassRepository(UserClassDatabase.getInstance(application))
    private val dataStore = SettingPreferences(application)

    val darkMode = dataStore.darkModeStatus.asLiveData()

    private val _startSavingTestData = MutableLiveData<Boolean?>()
    val startSavingTestData: LiveData<Boolean?>
        get() = _startSavingTestData

    fun insertData() {
        viewModelScope.launch {

            _startSavingTestData.value = true
        }
    }

    fun doneInsert() {
        _startSavingTestData.value = null
    }

}