package WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todonotes.NotesApp

class MyWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {

        var notesApp = applicationContext as NotesApp
        var notesDao = notesApp.getNotesDB().notesDao()
        notesDao.delete(true)           // because we want to delete when isTaskCompleted is true

        return Result.success()
    }

}