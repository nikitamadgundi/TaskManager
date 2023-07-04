package com.example.taskmanager

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TaskDatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Tasks.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Task"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DUESDATE = "DuesDate"
        private const val COLUMN_PRIORITY = "Priority"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_TITLE TEXT NOT NULL, $COLUMN_DESCRIPTION TEXT NOT NULL,$COLUMN_DUESDATE TEXT NOT NULL, $COLUMN_PRIORITY INT  )"
        try {
            db.execSQL(createTableQuery)
        } catch (e: SQLiteException) {
            Log.e("TaskDatabaseHelper", "Error creating database table: ${e.message}")
        }
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun updateTask(oldTitle: String, newTitle: String, newDescription: String,newDuesdate : String,newPriority : Int): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, newTitle)
            put(COLUMN_DESCRIPTION, newDescription)
            put(COLUMN_DUESDATE,newDuesdate)
            put(COLUMN_PRIORITY,newPriority)
        }
        val selection = "$COLUMN_TITLE = ?"
        val selectionArgs = arrayOf(oldTitle)
        val updatedRows = db.update(TABLE_NAME, values, selection, selectionArgs)
        db.close()
        return updatedRows
    }

    fun addTask( title: String, description: String, duesDate : String,priority : Int) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_DUESDATE,duesDate)
            put(COLUMN_PRIORITY,priority)
        }

        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteTaskByTitle(id: String): Int {
        val db = writableDatabase
        val selection = "$COLUMN_TITLE = ?" // Update the column name to COLUMN_ID
        val selectionArgs = arrayOf(id.toString()) // Convert id to a string
        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
        return deletedRows
    }

    @SuppressLint("Range")
    fun getAllTask(): List<Task> {
        val taskList = ArrayList<Task>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val duesDate = cursor.getString(cursor.getColumnIndex(COLUMN_DUESDATE))
                val priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY))
                val task = Task(title,description,priority,duesDate)
                taskList.add(task)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return taskList
    }



}