package com.example.mynotes.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(private val context: Context) {
    private val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
        }

        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readDbData () : ArrayList<String> {
        val dataList = ArrayList<String>()

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while(this?.moveToNext()!!) {
                val dataText = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()

        return dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }
}