package DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesData")
data class DBNotes(@PrimaryKey(autoGenerate = true)
                   var id: Int? = null,
                   @ColumnInfo(name = "title")
                   var title: String? = null,
                   @ColumnInfo(name = "desc")
                   var desc: String? = null,
                   @ColumnInfo(name = "imagePath")
                   var imagePath: String= "",
                   @ColumnInfo(name = "isTaskCompleted")
                   var isTaskCompleted: Boolean= false )