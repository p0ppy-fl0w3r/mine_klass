package com.atme.mineklass.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.atme.mineklass.MainActivity
import com.atme.mineklass.R
import java.util.concurrent.Executor

// TODO use viewPager for splash screen
class SplashActivity : AppCompatActivity() {

    private val DELAYTIME:Long = 1690

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAYTIME)


    }
}