package com.debajr.todolist.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.debajr.todolist.R
import com.debajr.todolist.databinding.ListItemTaskBinding
import com.debajr.todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ListItemTaskBinding) : RecyclerView.ViewHolder (binding.root){
        fun bind(task: Task) {
            binding.taskCardTitle.text = task.title
            binding.taskCardDescription.text = task.description
            binding.taskCardDate.text = task.date
            binding.taskCardTime.text = task.time

            binding.moreIv.setOnClickListener {
                showOptionMenu(task)
            }
        }

        private fun showOptionMenu(task: Task) {
            val ivMore = binding.moreIv
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_action_edit -> listenerEdit(task)
                    R.id.menu_action_delete -> listenerDelete(task)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }


    }

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.date == newItem.date &&
                    oldItem.time == newItem.time
        }

    }
}