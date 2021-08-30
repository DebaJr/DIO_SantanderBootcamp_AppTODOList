package com.debajr.todolist.ui

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.debajr.todolist.TaskApplication
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
import java.util.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewTaskBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.efbConfirmCreateTask.setOnClickListener {
            val task = Task(id=0, title = til_title.text,
                description = til_description.text,
                date = til_date.text,
                time = til_time.text)
            taskViewModel.insert(task)
            finish()
        }
    }
}