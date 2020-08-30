package com.example.todonotes

import Adapter.NotesAdapter
import DataBase.DBNotes
import MyInterface.InterfaceForClickListener
import WorkManager.MyWorker
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.concurrent.TimeUnit

class MyToDoActivity : AppCompatActivity() {

    private lateinit var floatingButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var list = ArrayList<DBNotes>()
    private val REQUEST_CODE = 1
    private lateinit var simpleCallBack: ItemTouchHelper.SimpleCallback
    public lateinit var sharedPreferencesList: SharedPreferences

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        bindView()
        getDataFromDB()
        setUpRecyclerViewTouchHelper()
        clickListener()
        setUpSharedPref()
        setUpRecycleView()
        setUpWorkManager()
        deleteList()
    }

    private fun bindView() {
        recyclerView = findViewById(R.id.recycleID)
        floatingButton = findViewById(R.id.floatButtonID)
    }

    private fun deleteList() {
        var intent = intent
        var toBeDeleted = intent.getBooleanExtra("DeleteUser", false)

        if (toBeDeleted) {
            list.clear()
            var notesApp = applicationContext as NotesApp
            var notesDao = notesApp.getNotesDB().notesDao()
            notesDao.deleteAll()
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerViewTouchHelper() {
        simpleCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT ){

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Adding progress Bar... Because Progress Dialog is deprecated
                var position = viewHolder.adapterPosition
                var deletedNotesDB : DBNotes  = list[position]      // jise delete krna hai use nikaal liya...
                deleteItemFromDB(deletedNotesDB)
                list.removeAt(position)
                adapter.notifyItemRemoved(position)

                Snackbar.make(recyclerView, deletedNotesDB.title.toString(), Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener{
                    list.add(position, deletedNotesDB)
                    var notesApp = applicationContext as NotesApp
                    var notesDao = notesApp.getNotesDB().notesDao()
                    notesDao.insert(deletedNotesDB)
                    adapter.notifyItemInserted(position)
                } ).show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@MyToDoActivity, R.color.deleteAlert))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_24px)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(this@MyToDoActivity, R.color.deleteAlert))
                        .addSwipeRightActionIcon(R.drawable.ic_delete_24px)
                        .create()
                        .decorate()
//                        .addBackgroundColor(ContextCompat.getColor(this@MyToDoActivity, R.color.deleteAlert))
//                        .addActionIcon(R.drawable.ic_baseline_delete_24)
//                        .create()
//                        .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

    private fun setUpRecycleView() {

        val myInterface = object : InterfaceForClickListener{
            override fun onClick(notesDB: DBNotes) {
                val intent = Intent(this@MyToDoActivity, DetailNotes::class.java)
                intent.putExtra("title", notesDB.title)
                intent.putExtra("description", notesDB.desc)
                intent.putExtra("imagePath",notesDB.imagePath)
                startActivity(intent)
            }

            override fun onUpdate(notesDB: DBNotes) {

                var notesApp = applicationContext as NotesApp
                var notesDao = notesApp.getNotesDB().notesDao()
                notesDao.updateNotes(notesDB)
            }
        }
        adapter = NotesAdapter(list, myInterface)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this@MyToDoActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL           // In Java..
        // linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.layoutManager = linearLayoutManager         // In Java
        //  recycler.setLayOutManager(linearLayout);

        //Adding recyclerview to ItemTouchHelper
//        var itemTouchHelper = ItemTouchHelper(simpleCallBack)          // Long way
//        itemTouchHelper.attachToRecyclerView(recyclerView)
        var itemTouchHelper = ItemTouchHelper(simpleCallBack).attachToRecyclerView(recyclerView)      // simple way


        sharedPreferencesList = getSharedPreferences("CheckList", Context.MODE_PRIVATE)
        var editor = sharedPreferencesList.edit()
        editor.putBoolean("isEmpty", list.isEmpty() )
        editor.apply()
        recyclerView.adapter = adapter                           // .setAdapter(adapter);
    }

    private fun deleteItemFromDB(notesDB: DBNotes) {
        var notesApp = applicationContext as NotesApp
        var notesDAO = notesApp.getNotesDB().notesDao()
        notesDAO.deleteNoteItem(notesDB)
    }

    private fun setUpSharedPref() {
        sharedPreferences = getSharedPreferences("saveToLocal", Context.MODE_PRIVATE)
    }

    private fun clickListener() {
        floatingButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // setUpDialog()
                var intent = Intent(this@MyToDoActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        })
    }

    private fun setUpWorkManager() {

        //  1) In Constraints ke chalte...
        //  2) ye request chalwani hai....
        //  3) chalwado....

        // these are the constraints when we want to do our work, like only while charging, etc.
        val constraints = Constraints.Builder().build()     // var constraints = Constraints.Builder().setRequiresBatteryNotLow().build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                                                            .setConstraints(constraints)
                                                            .build()
        WorkManager.getInstance().enqueue(request)

        // Or we can use as:
//        val request1 = OneTimeWorkRequest.Builder(MyWorker::class.java).setConstraints(constraints).build()
//
//        WorkManager.getInstance().beginWith(request1).then(request2).enqueue()
//
//        WorkManager.getInstance().beginWith(request1, request2, request3).then(request4).enqueue()
    }

    private fun getDataFromDB() {

        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDB().notesDao()
        list.addAll(notesDao.getAll())    // or var list1 = noteDao.getAll() then list.addAll(list1)
    }

    private fun addNotesToDB(notesDB: DBNotes) {
        // we will insert the notes here
        var notesApp = applicationContext as NotesApp
        var notesDao = notesApp.getNotesDB().notesDao()
        notesDao.insert(notesDB)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)  {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){

            var title = data?.getStringExtra("title")
            var desc = data?.getStringExtra("desc")
            var imagePath = data?.getStringExtra("imagePath")

            var notesDB = DBNotes(title = title!!, desc = desc!!, imagePath = imagePath!!, isTaskCompleted = false)

            addNotesToDB(notesDB)
            list.add(notesDB)

              recyclerView.adapter?.notifyDataSetChanged()
            //  recyclerView.adapter?.notifyItemChanged(list.size-1)
        }
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