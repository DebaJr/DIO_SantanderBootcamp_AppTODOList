package com.debajr.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.debajr.todolist.databinding.ActivityNewTaskBinding
import com.debajr.todolist.extensions.formatToStringPTBR
import com.debajr.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
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
    }
}