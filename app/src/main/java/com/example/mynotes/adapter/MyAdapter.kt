package com.example.mynotes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R

class MyAdapter (
    private val dataset: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        fun setData(title: String) {
            tvTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater.inflate(R.layout.rc_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(data: List<String>) {
        dataset.clear()
        dataset.addAll(data)
        notifyDataSetChanged()
    }
}