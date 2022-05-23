package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    private val imageRequestCode = 10
    private var imageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickSave(view: View) {
        val myTitle = edTitle.text.toString()
        val myDesk = edDesc.text.toString()

        if(myTitle != "" && myDesk != "") {
            myDbManager.insertToDb(myTitle, myDesk, imageUri)
        }

        startActivity(Intent(this, MainActivity::class.java))
    }

    fun openImageEditor(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        addImage.visibility = View.GONE
    }

    fun deleteImage(view: View) {
        mainImageLayout.visibility = View.GONE
        addImage.visibility = View.VISIBLE
    }

    fun editImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            imMainImage.setImageURI(data?.data)
            imageUri = data?.data.toString()
        }
    }
}