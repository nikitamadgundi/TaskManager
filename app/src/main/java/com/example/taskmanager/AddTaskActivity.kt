package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.taskmanager.databinding.ActivityAddTaskBinding
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        taskDatabaseHelper = TaskDatabaseHelper(this)
    }
    private fun setupListeners() {
        binding.btnSaveTask.setOnClickListener {
            val title = binding.edtTaskTitle.text.toString().trim()
            val description = binding.edtTaskDescription.text.toString().trim()
            val dueDate = binding.edtTaskDueDate.text.toString().trim()

            if (title.isEmpty()) {
                // Show an error message indicating that title is reqnfuired
                binding.edtTaskTitle.error = "Title is required"
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                // Show an error message indicating that description is required
                binding.edtTaskDescription.error = "Description is required"
                return@setOnClickListener
            }

            if (dueDate.isEmpty()) {
                // Show an error message indicating that due date is required
                binding.edtTaskDueDate.error = "Due date is required"
                return@setOnClickListener
            }

            val priority = when {
                binding.radioTaskPriorityLow.isChecked -> Task.PRIORITY_LOW
                binding.radioTaskPriorityNormal.isChecked -> Task.PRIORITY_NORMAL
                binding.radioTaskPriorityHigh.isChecked -> Task.PRIORITY_HIGH
                else -> {
                    // Show an error message indicating that priority is required
                    // This block will be executed if none of the radio buttons are selected
                    // You can display an error message or handle it as per your requirements
                    return@setOnClickListener
                }
            }



            // At this point, all the fields contain valid data
         // You can save the task or perform any other desired action



    taskDatabaseHelper.addTask(title.toString(),description.toString(),dueDate.toString(),priority.toInt())

            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    override fun onBackPressed() {
        val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}