package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_log_in.*

class CreateAcActivity : AppCompatActivity() {

    lateinit var userName: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var createButton: Button
    lateinit var loginBackButton: Button
    lateinit var sharedPreferences1: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences
    lateinit var sharedPreferencesListCheck: SharedPreferences
    lateinit var username: String
    lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ac)

        bindView()
        setUpSharedPref()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        createButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                sharedPreferencesListCheck = getSharedPreferences("CheckList", Context.MODE_PRIVATE)
                var isEmpty = sharedPreferencesListCheck.getBoolean("isEmpty", true)

                 username = userName.editText?.text.toString().trim()
                 pass = password.editText?.text.toString().trim()

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pass)){
                    userName.error = null
                    password.error = null

                    if (!isEmpty) {

                        var deleteIntent = Intent(this@CreateAcActivity, MyToDoActivity::class.java)
                        deleteIntent.putExtra("DeleteUser", true)
                        saveUser()
                        saveLoginStatus()
                        startActivity(deleteIntent)
                        finish()
                    }else {
                        saveUser()
                        saveLoginStatus()
                        startActivity(Intent(this@CreateAcActivity, MyToDoActivity::class.java))
                        finish()
                    }
                }
                else{
                    if (username.isNotEmpty())
                        userName.error = null
                    else
                        userName.error = "Required"

                    if (pass.isNotEmpty())
                        password.error = null
                    else
                        password.error = "Required"
                }
            }
        })

        loginBackButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@CreateAcActivity, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun saveUser() {
        var editor1 = sharedPreferences1.edit()
        editor1.putString("userName",username)
        editor1.putString("password", pass)
        editor1.apply()
    }

    private fun saveLoginStatus() {
        var editor2 = sharedPreferences2.edit()
        editor2.putBoolean("isLogin", true)
        editor2.apply()
    }

    private fun setUpSharedPref() {
        sharedPreferences1 = getSharedPreferences("CreateUser", Context.MODE_PRIVATE)
        sharedPreferences2 = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
    }

    private fun bindView() {
        userName = findViewById(R.id.CreateUserID)
        password = findViewById(R.id.createPassID)
        createButton = findViewById(R.id.createButtonID)
        loginBackButton = findViewById(R.id.logInBackID)
    }
}