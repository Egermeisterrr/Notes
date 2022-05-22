package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val imageRequestCode = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    fun onClickSave(view: View) {
        myDbManager.insertToDb(edTitle.text.toString(), edDesc.text.toString())
    }

    fun openImageEditor(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        addImage.visibility = View.GONE
    }

    fun deleteImage(view: View) {
        mainImageLayout.visibility = View.GONE
        addImage.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}