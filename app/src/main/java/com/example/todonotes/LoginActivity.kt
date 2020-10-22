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
    lateinit var Email: TextInputLayout
    lateinit var Pass: TextInputLayout
    lateinit var loginButton: Button
    lateinit var createGoButton: Button
    lateinit var sharedPreferences1: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        bindView()
        setUpSharedPref()
        clickListeners()
    }

    private fun clickListeners() {

        loginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var email = Email.editText?.text.toString().trim()
                var pass = Pass.editText?.text.toString().trim()

                if (email.isNotEmpty() and pass.isNotEmpty()) {

                    Email.error = null
                    Pass.error = null

                    if (email == sharedPreferences2.getString("userName", null) &&
                            pass == sharedPreferences2.getString("password", null)) {

                        var intent = Intent(this@LoginActivity, MyToDoActivity::class.java)
                        startActivity(intent)
                        // saving session or login state
                        saveLoginStatus()
                        finish()
                    } else
                        Toast.makeText(this@LoginActivity, "User doesn't exist", Toast.LENGTH_SHORT).show()
                } else {
                    if (email.isNotEmpty())
                        Email.error = null
                    else
                        Email.error = "Required"

                    if (pass.isNotEmpty())
                        Pass.error = null
                    else
                        Pass.error = "Required"
                }
            }
        })

        createGoButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@LoginActivity, CreateAcActivity::class.java))
                finish()
            }
        })
    }

    private fun setUpSharedPref() {
        sharedPreferences1 = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
        sharedPreferences2 = getSharedPreferences("CreateUser", Context.MODE_PRIVATE)
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences1.edit()
        editor.putBoolean("isLogin", true)
        editor.apply()
    }

    private fun bindView() {
        Email = findViewById(R.id.textInputLayout)
        Pass = findViewById(R.id.textInputLayout2)
        loginButton = findViewById(R.id.loginButtonID)
        createGoButton = findViewById(R.id.createGoID)
    }
}