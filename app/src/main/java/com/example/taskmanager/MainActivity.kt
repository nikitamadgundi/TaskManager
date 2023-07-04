package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tasksList : ArrayList<Task> = ArrayList<Task>()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpdatabase()
        setUpTaskListRecyclerView()
        setUpListner()

        getData()
    }

    private fun setUpListner() {
        binding.btnAddTask.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun setUpTaskListRecyclerView() {

        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        binding.recyclerTask.layoutManager = layoutManager


        taskAdapter = TaskAdapter(tasksList)

        binding.recyclerTask.adapter = taskAdapter

        taskAdapter.setOnClickListener = SetOnClickListener()

    }


    inner class SetOnClickListener : TaskAdapter.SetOnClickListener {
        override fun setOnClick(position : Int) {

            val intent = Intent(this@MainActivity, TaskDetailsActivity::class.java)
            intent.putExtra("taskTitle", tasksList[position].title)
            intent.putExtra("taskDescription", tasksList[position].description)
            intent.putExtra("taskDuedate", tasksList[position].dueDate)
            intent.putExtra("taskPriority", tasksList[position].priority)
            startActivity(intent)
            finish()


        }

    }

    private fun setUpdatabase() {
        taskDatabaseHelper = TaskDatabaseHelper(this@MainActivity)
    }


    private fun getData() {

        val TaskList2 = taskDatabaseHelper.getAllTask()

        for (i in TaskList2) {

            tasksList.add(i)

        }

    }

    override fun onResume() {
        super.onResume()
        RefreshTaskListt() // Refresh the notes list when the fragment comes back on the screen
    }


    private fun RefreshTaskListt() {
        tasksList.clear() // Clear the existing notes list
        tasksList.addAll(taskDatabaseHelper.getAllTask()) // Fetch new data from the database
        // Update your UI or adapter with the refreshed notesList
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database connection when the fragment is destroyed
        taskDatabaseHelper.close()
    }



}