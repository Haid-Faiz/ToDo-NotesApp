package com.example.todonotes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

public class DetailNotes : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_in_detail)

        bindView()
        setUpIntent()
    }

    private fun setUpIntent() {
        var intent = intent  // It is getIntent() in Kotlin
        var str1 = intent.getStringExtra("title")
        var str2 : String? = intent.getStringExtra("description")     // Error is coming without question mark

        title.setText(str1)  // we can also write this way... but not recommended
        desc.text = str2 // this is Setters in Kotlin
    }

    private fun bindView() {
        title = findViewById(R.id.textView2)
        desc = findViewById(R.id.textView3)
    }
}