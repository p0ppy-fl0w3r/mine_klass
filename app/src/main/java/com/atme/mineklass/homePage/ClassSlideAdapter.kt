package com.atme.mineklass.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atme.mineklass.ClassSlideWrapper
import com.atme.mineklass.databinding.ClassItemBinding

class ClassSlideAdapter():ListAdapter<ClassSlideWrapper, ClassSlideAdapter.ClassSlideViewHolder>(SlideDiffUtils()) {

    class SlideDiffUtils: DiffUtil.ItemCallback<ClassSlideWrapper>(){
        override fun areItemsTheSame(oldItem: ClassSlideWrapper, newItem: ClassSlideWrapper): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: ClassSlideWrapper, newItem: ClassSlideWrapper): Boolean {
            return oldItem == newItem
        }

    }

    class ClassSlideViewHolder(private val binding: ClassItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(slideWrapper: ClassSlideWrapper){

            val adapter = TitleRecyclerAdapter(slideWrapper.day)
            binding.titleRecycler.adapter = adapter
            adapter.addHeaderAndSubmitList(slideWrapper.classes)
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ClassSlideViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ClassItemBinding.inflate(inflater, parent, false)
                return ClassSlideViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassSlideViewHolder {
        return ClassSlideViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ClassSlideViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}