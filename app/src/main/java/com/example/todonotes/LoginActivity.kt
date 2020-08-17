package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    lateinit var Email : TextInputLayout
    lateinit var Pass : TextInputLayout
    lateinit var loginButton : Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        bindView()

        loginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var email = Email.editText?.text.toString()
                var pass = Pass.editText?.text.toString()

                if (email.isNotEmpty() and pass.isNotEmpty()){

                    var intent = Intent(this@LoginActivity, MyToDoActivity::class.java)
                    startActivity(intent)
                    // saving session or login state
                    saveLoginStatus()
                }
                else
                    Toast.makeText(this@LoginActivity, "Fields can't be empty", Toast.LENGTH_SHORT)

            }

        })
    }

    private fun saveLoginStatus() {
        sharedPreferences = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", true)
        editor.apply()
    }

    private fun bindView() {
        Email = findViewById(R.id.textInputLayout)
        Pass = findViewById(R.id.textInputLayout2)
        loginButton = findViewById(R.id.buttonID)
    }
}