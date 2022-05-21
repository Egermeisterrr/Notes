package com.example.mynotes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
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

    }
}