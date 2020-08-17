package com.example.todonotes

import Adapter.NotesAdapter
import DataBase.DBNotes
import MyInterface.InterfaceForClickListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class MyToDoActivity : AppCompatActivity() {

    private lateinit var floatingButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var list = ArrayList<DBNotes>()
    private val REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        bindView()
        getDataFromDB()

        sharedPreferences = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
        floatingButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               // setUpDialog()
                var intent = Intent(this@MyToDoActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        })
        setUpRecycleView()
    }

    private fun getDataFromDB() {

        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDB().notesDao()
        list.addAll(notesDao.getAll())    // or var list1 = noteDao.getAll() then list.addAll(list1)
    }

    private fun setUpDialog() {
        var view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        var Title: TextInputLayout = view.findViewById(R.id.textInputLayID1)
        var Description: TextInputLayout = view.findViewById(R.id.textInputLayID2)
        var saveButton: Button = view.findViewById(R.id.saveButtonID)

        var alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()
        alertDialog.show()

        saveButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var title = Title.editText?.text.toString()
                var description = Description.editText?.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    var notesDB = DBNotes(title = title, desc =  description)   /* KOTLIN DATA CLASS [PRIMARY CONSTRUCTOR FUNDA]
                                                               In Java we do this as...
                                                               Notes notes = new Notes();
                                                               notes.setTitle(title);
                                                               notes.setDesc(desc);    */
                    list.add(notesDB)
                    addNotesToDB(notesDB)
//                    setUpRecycleView()
                    alertDialog.hide()
                }
                else  Toast.makeText(this@MyToDoActivity, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addNotesToDB(notesDB: DBNotes) {
        // we will insert the notes here
        var notesApp = applicationContext as NotesApp
        var notesDao = notesApp.getNotesDB().notesDao()
        notesDao.insert(notesDB)
    }

    private fun setUpRecycleView() {

        val myInterface = object : InterfaceForClickListener{
            override fun onClick(notesDB: DBNotes) {
                val intent = Intent(this@MyToDoActivity, DetailNotes::class.java)
                intent.putExtra("title", notesDB.title)
                intent.putExtra("desc", notesDB.desc)
                startActivity(intent)
            }

            override fun onUpdate(notesDB: DBNotes) {

                var notesApp = applicationContext as NotesApp
                var notesDao = notesApp.getNotesDB().notesDao()
                notesDao.updateNotes(notesDB)
            }
        }
        val adapter = NotesAdapter(list, myInterface)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this@MyToDoActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL           // In Java..
                                                                          // linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.layoutManager = linearLayoutManager         // In Java
                                                                 //  recycler.setLayOutManager(linearLayout);
        recyclerView.adapter = adapter                           // .setAdapter(adapter);

    }

    private fun bindView() {
        floatingButton = findViewById(R.id.floatButtonID)
        recyclerView = findViewById(R.id.recycleID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)  // In Java: getMenuInflater()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logoutButtonID){

            startActivity(Intent(this@MyToDoActivity, LoginActivity::class.java))
            editor = sharedPreferences.edit()
            editor.putBoolean("isLogin", false)
            editor.apply()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}