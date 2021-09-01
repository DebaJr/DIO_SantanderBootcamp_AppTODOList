package com.debajr.todolist.ui

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.debajr.todolist.TaskApplication
import com.debajr.todolist.databinding.ActivityEditOrRemoveTaskBinding
import com.debajr.todolist.databinding.ActivityNewTaskBinding
import com.debajr.todolist.extensions.formatToStringPTBR
import com.debajr.todolist.extensions.text
import com.debajr.todolist.model.Task
import com.debajr.todolist.model.TaskViewModel
import com.debajr.todolist.model.TaskViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditOrRemoveTaskBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditOrRemoveTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)

            var viewModelJob = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
            uiScope.launch() {
                val myTask = taskViewModel.getTaskByID(taskId)
                binding.tilTitle.text = myTask.title
                binding.tilDescription.text = myTask.description
                binding.tilDate.text = myTask.date
                binding.tilTime.text = myTask.time
            }
        }

        addListeners()
    }

    private fun addListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZoneOffset = TimeZone.getDefault().getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + timeZoneOffset).formatToStringPTBR()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(if (is24HourFormat(this)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H )
                .build()
            timePicker.addOnPositiveButtonClickListener {
                binding.tilTime.text = "${String.format("%02d", timePicker.hour)}:${String.format("%02d", timePicker.minute)}"
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.efbConfirmEditTask.setOnClickListener {
            val task = Task(id=intent.getIntExtra(TASK_ID, 0), title = til_title.text,
                description = til_description.text,
                date = til_date.text,
                time = til_time.text)
            taskViewModel.update(task)
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task id"
    }
}