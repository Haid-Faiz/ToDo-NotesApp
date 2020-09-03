package com.example.todonotes

import OnBoard.OnBoardActivity
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    lateinit var sharedPreferences1: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences
    private val delayTime = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // i have disconnected it's layout to make real Splash screen
        //setContentView(R.layout.activity_splash_screen)
      //  Thread.sleep(1000)      it also holds the screen i mean that thread
        supportActionBar?.hide()
        setUpSharedPref()
        timeHandler()
    }

    private fun setUpSharedPref() {
        sharedPreferences1 = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
        sharedPreferences2 = getSharedPreferences("OnBoardedValue", Context.MODE_PRIVATE)
    }

    private fun timeHandler() {

        Handler().postDelayed({  if (sharedPreferences1.getBoolean("isLogin", false)) {
                        startActivity(Intent(this@SplashScreen, MyToDoActivity::class.java))
                        finish()
                    }
                    else {

                        if(sharedPreferences2.getBoolean("isOnBoarded",false))
                            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                        else
                            startActivity(Intent(this@SplashScreen, OnBoardActivity::class.java))

                        finish()
                    }}, delayTime.toLong())
    }
}