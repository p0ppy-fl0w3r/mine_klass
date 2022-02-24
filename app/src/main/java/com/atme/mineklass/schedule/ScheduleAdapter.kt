package com.atme.mineklass.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atme.mineklass.R
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.databinding.ScheduleItemBinding
import timber.log.Timber
import java.util.*


class ScheduleAdapter(private val clickListener: OnClickListener) :
    ListAdapter<ClassData, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallback()) {

    class ScheduleDiffCallback : DiffUtil.ItemCallback<ClassData>() {
        override fun areItemsTheSame(oldItem: ClassData, newItem: ClassData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ClassData, newItem: ClassData): Boolean {
            return oldItem == newItem
        }
    }

    class ScheduleViewHolder private constructor(private val binding: ScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ClassData) {
            binding.classItem = item

            binding.constraintLayout.background =
                ContextCompat.getDrawable(
                    binding.constraintLayout.context,
                   when(item.isToday){
                       true -> R.drawable.today_card
                       else -> R.drawable.item_card_rect
                   }
                )


            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ScheduleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScheduleItemBinding.inflate(layoutInflater, parent, false)
                return ScheduleViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentClassData = getItem(position)
        holder.itemView.setOnClickListener { clickListener.onClick(currentClassData) }
        holder.bind(currentClassData)
    }

}

class OnClickListener(val clickListener: (ClassData) -> Unit) {
    fun onClick(classData: ClassData) = clickListener(classData)
}