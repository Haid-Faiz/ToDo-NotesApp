package DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todonotes.NotesApp


@Database(entities = [DBNotes::class], version = 1)       // here to add table class in our database
abstract class NotesDatabase: RoomDatabase(){

    abstract fun notesDao(): NotesDAO

    // jise bhi public static bnana ho use "companion object" me daalo
    companion object{
        lateinit var INSTANCE: NotesDatabase

        fun getInstance(context: Context): NotesDatabase {

            synchronized(NotesDatabase::class) {       // synchronized means it will run on main thread
                INSTANCE = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "MyDatabase")
                           .allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}

