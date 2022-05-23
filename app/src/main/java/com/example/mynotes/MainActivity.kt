package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.adapter.MyAdapter
import com.example.mynotes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    private val myAdapter = MyAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun add(view: View) {
        startActivity(Intent(this, EditActivity::class.java))
    }

    private fun init() {
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = myAdapter
    }

    fun fillAdapter() {
        myAdapter.updateAdapter(myDbManager.readDbData())
    }
}