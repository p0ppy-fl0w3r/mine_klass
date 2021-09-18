package com.atme.mineklass.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atme.mineklass.R
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.databinding.TitleHeaderBinding
import com.atme.mineklass.databinding.TitleScheduleItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.ClassCastException

private const val HEADER_ITEM: Int = 0
private const val USER_ITEM: Int = 1
private val FULL_DAYS = hashMapOf(
    "SUN" to "Sunday",
    "MON" to "Monday",
    "TUE" to "Tuesday",
    "WED" to "Wednesday",
    "THU" to "Thursday",
    "FRI" to "Friday",
    "SAT" to "Saturday"
)

class TitleRecyclerAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(ItemDiffUtils()) {

    private val adapterScope = CoroutineScope(Dispatchers.IO)

    class ItemDiffUtils : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> HEADER_ITEM
            is DataItem.UserClassDataItem -> USER_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            USER_ITEM -> TitleViewHolder.from(parent)
            HEADER_ITEM -> HeaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val mUserItem = getItem(position) as DataItem.UserClassDataItem
                holder.bind(mUserItem.userClassData)
            }

            is HeaderViewHolder -> {
                val mHeaderItem = getItem(position) as DataItem.Header
                holder.bind(mHeaderItem)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<UserClassData>?) {

        adapterScope.launch {
            if (list != null) {
                when (list.size) {
                    // Empty list usually means saturday but just to be safe adding a different message
                    0 -> {
                        withContext(Dispatchers.Main) {
                            submitList(listOf(DataItem.Header("No classes today!!")))

                        }
                    }

                    else -> {
                        val curDay = list[0].day
                        val completeList =
                            listOf(DataItem.Header(curDay)) + list.map {
                                DataItem.UserClassDataItem(
                                    it
                                )
                            }
                        withContext(Dispatchers.Main) {
                            submitList(completeList)
                        }
                    }
                }
            } else {
                submitList(listOf(DataItem.Header("Error!!")))

            }
        }
    }
}


class TitleViewHolder(private val binding: TitleScheduleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(userItem: UserClassData) {
        binding.classData = userItem
    }

    companion object {
        fun from(parent: ViewGroup): TitleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<TitleScheduleItemBinding>(
                inflater,
                R.layout.title_schedule_item,
                parent,
                false
            )
            return TitleViewHolder(binding)
        }
    }
}

class HeaderViewHolder(private val binding:TitleHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: DataItem.Header) {
        var headerText = FULL_DAYS[header.day]
        if(headerText.isNullOrBlank()){
            headerText = header.day
        }
        binding.headerText.text = headerText
        binding.textLinearLayout.setBackgroundResource(R.drawable.text_rect)
    }

    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = TitleHeaderBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(view)
        }
    }
}

sealed class DataItem {

    abstract val id: String

    data class UserClassDataItem(val userClassData: UserClassData) : DataItem() {
        override val id: String = userClassData.id.toString()
    }

    data class Header(val day: String) : DataItem() {
        override val id: String = "Header"
    }
}