package com.example.todonotes

import DataBase.NotesDatabase
import android.app.Application
import android.content.Context

class NotesApp : Application() {    // the class which get extended by Application()...... will be added in menifest as name.

    override fun onCreate() {
        super.onCreate()
    }
        //To make the database accessible
        fun getNotesDB(): NotesDatabase {
            return NotesDatabase.getInstance(this)
        }
}