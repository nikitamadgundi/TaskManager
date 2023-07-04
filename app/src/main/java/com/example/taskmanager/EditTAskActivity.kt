package com.example.taskmanager

import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.databinding.EditTaskViewBinding
import com.example.taskmanager.databinding.TaskViewBinding
import java.util.Calendar

class EditTAskActivity : AppCompatActivity() {

    private lateinit var binding: EditTaskViewBinding
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper
    private  var title = ""
    private  var description = ""
    private  var DueDate = ""
    private  var priority = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditTaskViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDatabaseHelper = TaskDatabaseHelper(this)

        title = intent.getStringExtra("taskTitle").toString()
        description = intent.getStringExtra("taskDescription").toString()
        priority = intent.getStringExtra("taskPriority").toString()
        DueDate = intent.getStringExtra("taskDuedate").toString()

        binding.edtTaskTitle.setText(title)
        binding.edtTaskDescription.setText(description)
        binding.edtTaskDueDate.setText(DueDate)
        val priority = if(binding.radioTaskPriorityLow.isChecked) {
            Task.PRIORITY_LOW
        }
        else {
            if(binding.radioTaskPriorityNormal.isChecked) {
                Task.PRIORITY_NORMAL
            }
            else {
                Task.PRIORITY_HIGH
            }
        }


        binding.btnSaveTask.setOnClickListener {


            val oldTitle = title
            val newTitle = binding.edtTaskTitle.text.toString()
            val newDescription = binding.edtTaskDescription.text.toString()
            val newDueDate = binding.edtTaskDueDate.text.toString()
            val priority = if(binding.radioTaskPriorityLow.isChecked) {
                Task.PRIORITY_LOW
            }
            else {
                if(binding.radioTaskPriorityNormal.isChecked) {
                    Task.PRIORITY_NORMAL
                }
                else {
                    Task.PRIORITY_HIGH
                }
            }



            val updatedRows = taskDatabaseHelper.updateTask(oldTitle ,newTitle, newDescription,newDueDate,priority)

            val intent = Intent(this@EditTAskActivity, MainActivity::class.java)
            startActivity(intent)

            finish()

        }


    }

    }
