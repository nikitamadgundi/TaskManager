package com.example.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.TaskViewBinding

class TaskAdapter (val TaskList : ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface SetOnClickListener{
        fun setOnClick(position: Int)
    }

    var setOnClickListener: SetOnClickListener? = null

    fun setOnClickListener(listener: SetOnClickListener) {
        setOnClickListener = listener
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){

         var txtTitle : TextView = view.findViewById(R.id.txtTaskTitle)
        var txtDescription: TextView = view.findViewById(R.id.txtDescription)
        var txtDuedate: TextView = view.findViewById(R.id.txtTaskDueDate)
        var txtPriority: TextView = view.findViewById(R.id.txtTaskPriority)


        init {
            view.setOnClickListener {
                setOnClickListener?.setOnClick(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)

        return TaskViewHolder(view)
    }

        override fun getItemCount() = TaskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = TaskList[position]

        holder.txtTitle.text = task.title
        holder.txtDescription.text = task.description
        holder.txtDuedate.text = task.dueDate
        holder.txtPriority.text = task.priority.toString()

    }
}
