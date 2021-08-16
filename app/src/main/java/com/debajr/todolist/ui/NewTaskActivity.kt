package com.debajr.todolist.ui

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.debajr.todolist.databinding.ActivityNewTaskBinding
import com.debajr.todolist.extensions.formatToStringPTBR
import com.debajr.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_new_task.*
import java.util.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewTaskBinding

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
            Log.e("CREATE TASK BTN", "Clicado no botão de confirmar a criação de nova tarefa / evento")
        }
    }
}