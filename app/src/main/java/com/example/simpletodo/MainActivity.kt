package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. remove the item from the list
                listOfTasks.removeAt(position)
                // 2. notify the adapter that data set has been changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }


        // load items before anything else
        loadItems()

        // look up recycler view in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // attach adapter to the recycler view
        recyclerView.adapter = adapter
        // set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up the button and input field so that the user can enter a task

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // get reference to the button
        // and the set an onclicklistener

        findViewById<Button>(R.id.button).setOnClickListener{
            // 1. grab the text the user has inputted
            val userInputtedTask = inputTextField.text.toString()

            // 2. add the string to list
            listOfTasks.add(userInputtedTask)

            // notify the adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. reset text field
            inputTextField.setText("")

            saveItems()

        }
    }

    //Save data that user inputted
    // Saving by writing and reading from a file

    // Create a method to get the file we need
    fun getDataFile() : File {

        // every line will be a task in list of task
        return File(filesDir, "tasks.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }

    }

    // Save (write) items into the data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }

    }
}