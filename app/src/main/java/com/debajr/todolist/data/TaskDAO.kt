package com.debajr.todolist.data

import androidx.room.*
import com.debajr.todolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    suspend fun getSimpleListAllTasks() : List<Task>


    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table WHERE id = :id LIMIT 1")
    fun getTaskById(id: Int) : Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun deleteTask(vararg task: Task)

    @Update
    suspend fun update(task: Task)
}