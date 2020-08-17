package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private val delayTime = 2300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)

        timeHandler()
    }

    private fun timeHandler() {

        Handler().postDelayed({
            if (sharedPreferences.getBoolean("isLogin", false))
                startActivity(Intent(this@SplashScreen, MyToDoActivity::class.java))
            else
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            finish()

        }, delayTime.toLong())
    }
}