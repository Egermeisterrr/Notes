package com.example.mynotes.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyDbManager(private val context: Context) {
    private val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    suspend fun insertToDb(title: String, content: String, uri: String) =
        withContext(Dispatchers.IO) {
            val values = ContentValues().apply {
                put(MyDbNameClass.COLUMN_NAME_TITLE, title)
                put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
                put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)
            }

            db?.insert(MyDbNameClass.TABLE_NAME, null, values)
        }

    suspend fun updateItem(title: String, content: String, uri: String, id: Int) =
        withContext(Dispatchers.IO) {
            val selection = BaseColumns._ID + "=$id"
            val values = ContentValues().apply {
                put(MyDbNameClass.COLUMN_NAME_TITLE, title)
                put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
                put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)
            }

            db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
        }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    @SuppressLint("Range")
    suspend fun readDbData(searchText: String): ArrayList<ListItem> = withContext(Dispatchers.IO) {
        val dataList = ArrayList<ListItem>()
        val selection = "${MyDbNameClass.COLUMN_NAME_TITLE} like ?"

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,
            null,
            selection,
            arrayOf("%$searchText%"),
            null,
            null,
            null
        )

        with(cursor) {
            while (this?.moveToNext()!!) {
                val dataText =
                    cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataContent =
                    cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataUri =
                    cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_IMAGE_URI))
                val dataId =
                    cursor?.getInt(cursor.getColumnIndex(BaseColumns._ID))

                var item = ListItem(dataText, dataContent, dataUri, dataId)
                dataList.add(item)
            }
        }
        cursor?.close()

        return@withContext dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }
}