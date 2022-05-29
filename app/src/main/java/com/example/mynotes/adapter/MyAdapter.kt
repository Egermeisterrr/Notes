package com.example.mynotes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.EditActivity
import com.example.mynotes.R
import com.example.mynotes.db.ListItem
import com.example.mynotes.db.MyDbManager
import com.example.mynotes.db.MyIntentConstants

class MyAdapter(
    private val dataset: ArrayList<ListItem>,
    private val context: Context
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(
        private val view: View,
        contextV: Context
    ) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val context =  contextV

        fun setData(item: ListItem) {
            tvTitle.text = item.title
            itemView.setOnClickListener{
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(MyIntentConstants.I_TITLE_KEY, item.title)
                    putExtra(MyIntentConstants.I_DESK_KEY, item.desk)
                    putExtra(MyIntentConstants.I_URI_KEY, item.uri)
                    putExtra(MyIntentConstants.I_ID_KEY, item.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(data: List<ListItem>) {
        dataset.clear()
        dataset.addAll(data)
        notifyDataSetChanged()
    }

    fun removeItem(pos: Int, dbManager: MyDbManager) {
        dbManager.removeItemFromDb(dataset[pos].id.toString())
        dataset.removeAt(pos)
        notifyItemRangeChanged(0, dataset.size)
        notifyItemRemoved(pos)
    }
}