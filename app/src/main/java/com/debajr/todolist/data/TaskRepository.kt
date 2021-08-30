package com.debajr.todolist.data

import androidx.annotation.WorkerThread
import com.debajr.todolist.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TaskRepository (val taskDao: TaskDAO) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.
        val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

        // By default Room runs suspend queries off the main thread, therefore, we don't need to
        // implement anything else to ensure we're not doing long running database work
        // off the main thread.
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(task: Task) {
                taskDao.insert(task)
        }

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun update(task: Task) {
                taskDao.update(task)
        }

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun deleteTask(task: Task) {
                taskDao.deleteTask(task)
        }

}