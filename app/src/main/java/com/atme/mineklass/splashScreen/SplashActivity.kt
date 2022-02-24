package com.atme.mineklass.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.Constants
import com.atme.mineklass.MainActivity
import com.atme.mineklass.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel : SplashViewModel by lazy { ViewModelProvider(this)[SplashViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.insertData()

        viewModel.startSavingTestData.observe(this){
            if (it == true){
                viewModel.doneInsert()
                startMainActivity()
            }
        }


    }

    private fun startMainActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Constants.DELAY_TIME)
    }
}