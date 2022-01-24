package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * A bridge that tells recycler view how to display the data we give it
 */
class TaskItemAdapter(val listOfItems : List<String>, val longClickListener : OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from ML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //Stores references to elements in our layout view
        val textView : TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }


}