package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.db.MyDbManager
import com.example.mynotes.db.MyIntentConstants
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    private val imageRequestCode = 10
    private var imageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        getMyIntents()
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

        if (myTitle != "" && myDesk != "") {
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            imMainImage.setImageURI(data?.data)
            imageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    fun getMyIntents() {
        val i = intent

        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {
                addImage.visibility = View.GONE
                edTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(MyIntentConstants.I_DESK_KEY))
                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty") {
                    mainImageLayout.visibility = View.VISIBLE
                    imMainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.I_URI_KEY)))
                    editImage.visibility = View.GONE
                    deleteImage.visibility = View.GONE
                }
            }
        }
    }
}