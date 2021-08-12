package com.debajr.todolist.extensions


import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

val locale = Locale.getDefault()

fun Date.formatToStringPTBR() = SimpleDateFormat("dd/MM/yyyy", locale).format(this)

var TextInputLayout.text : String
    get() = editText?.text.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }