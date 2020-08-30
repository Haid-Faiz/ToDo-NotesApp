package DataBase

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


// DAO - Data Access Objects
@Dao
interface NotesDAO {

    @Query("Select * from notesData")   // here notesData is table name
    fun getAll() : List<DBNotes>

    @Insert(onConflict = REPLACE)
    fun insert(notesDB: DBNotes)

    @Update
    fun updateNotes(notesDB: DBNotes)

    @Delete
    fun deleteNoteItem(notesDB: DBNotes)

    @Query("Delete from notesData")
    fun deleteAll()

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")    //  :status hai ye
    fun delete(status : Boolean)
}