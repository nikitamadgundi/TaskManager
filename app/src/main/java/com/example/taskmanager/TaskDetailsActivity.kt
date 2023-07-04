package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.taskmanager.databinding.ActvityDetailsTaskBinding

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActvityDetailsTaskBinding
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper
   private var id = ""
    private var title = ""
    private var Description = ""
    private var Duedate = ""
    private var priority = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvityDetailsTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDatabaseHelper = TaskDatabaseHelper(this)

        title = intent.getStringExtra("taskTitle").toString()
        Description = intent.getStringExtra("taskDescription").toString()
        Duedate = intent.getStringExtra("taskDuedate").toString()
        priority = intent.getIntExtra("taskPriority", 0).toString()

        binding.txtTaskTitle1.text = title
        binding.txtDescription1.text = Description
        binding.txtTaskDueDate1.text = Duedate
        binding.txtTaskPriority1.text = priority

        setUpListeners()

    }



    private fun setUpListeners() {

        binding.imgDeleteNote.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Item")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setPositiveButton("Delete") { dialog, which ->
                val id = id
                val deletedRows = taskDatabaseHelper.deleteTaskByTitle(id)

                val intent = Intent(this@TaskDetailsActivity, MainActivity::class.java)
                startActivity(intent)

                finish()
            }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }


        binding.imgEditTask.setOnClickListener {
            val intent = Intent(this@TaskDetailsActivity, EditTAskActivity::class.java)
            intent.putExtra("taskTitle",binding.txtTaskTitle1.text.toString())
            intent.putExtra("taskDescription",binding.txtDescription1.text.toString())
            intent.putExtra("taskDuedate",binding.txtTaskDueDate1.text.toString())
            intent.putExtra("taskPriority",binding.txtTaskPriority1.text.toString())
            startActivity(intent)
            finish()

        }

        binding.btnSaveTask.setOnClickListener {
            val intent = Intent(this@TaskDetailsActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

    override fun onBackPressed() {

        val intent = Intent(this@TaskDetailsActivity, MainActivity::class.java)
        startActivity(intent)

        finish()

    }
}