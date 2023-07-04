package com.example.taskmanager



data class Task(

   // val id : Int ,
    var title: String,
    var description: String?,
    var priority: Int = 2,
    var dueDate: String = "23-04-2023"
) : java.io.Serializable {
    companion object {
        val PRIORITY_LOW = 1
        val PRIORITY_NORMAL = 2
        val PRIORITY_HIGH = 3
    }
}

