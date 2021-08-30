package com.debajr.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
class Task(@PrimaryKey(autoGenerate = true) val id: Int,
           @ColumnInfo(name = "title") val title: String,
           @ColumnInfo(name = "description") val description: String,
           @ColumnInfo(name = "date") val date: String,
           @ColumnInfo(name = "time") val time: String
)
