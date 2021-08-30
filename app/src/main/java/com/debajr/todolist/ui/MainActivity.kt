package com.debajr.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.debajr.todolist.TaskApplication
import com.debajr.todolist.databinding.ActivityMainBinding
import com.debajr.todolist.model.TaskViewModel
import com.debajr.todolist.model.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    private val newTaskActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tasksRv.adapter = adapter
        binding.tasksRv.layoutManager = LinearLayoutManager(this)


        insertListeners()

        taskViewModel.allTasks.observe(this, Observer { tasks ->
            // Update the cached copy of the words in the adapter.
            tasks?.let { adapter.submitList(it) }
        })

    }

    private fun insertListeners() {
        binding.efbCreateTask.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java))
        }

        adapter.listenerEdit = {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra(EditTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, EDIT_TASK)
        }
        adapter.listenerDelete = { taskViewModel.deleteTask(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TASK && resultCode == AppCompatActivity.RESULT_OK){

        }
    }

    companion object {
        private const val EDIT_TASK = 1
    }

}