package MyInterface

import DataBase.DBNotes

interface InterfaceForClickListener {
    fun onClick(notesDB: DBNotes)
    fun onUpdate(notesDB: DBNotes)
}