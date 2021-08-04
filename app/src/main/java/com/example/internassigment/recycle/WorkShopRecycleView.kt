package com.example.internassigment.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.databinding.SubjectItemBinding
import com.example.internassigment.databinding.UserDescLayoutBinding

class WorkShopRecycleView constructor(private val itemClicked: (CourseName) -> Unit) :
    ListAdapter<AllData, AllViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
        return when (viewType) {
            R.layout.user_desc_layout -> {
                AllViewHolder.UserInfo(
                    binding = UserDescLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.subject_item -> {
                AllViewHolder.SubjectItem(
                    binding = SubjectItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val current = getItem(position)
        current?.let { allData ->
            when (holder) {
                is AllViewHolder.SubjectItem -> holder.bindIt(
                    subjectInfo = allData as AllData.Course,
                    position,
                    itemClicked
                )
                is AllViewHolder.UserInfo -> holder.bindIt(users = allData as AllData.Users)
            }
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is AllData.Course -> R.layout.subject_item
        is AllData.Users -> R.layout.user_desc_layout
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AllData>() {
            override fun areItemsTheSame(oldItem: AllData, newItem: AllData) =
                getValue(oldItem) == getValue(newItem)

            override fun areContentsTheSame(oldItem: AllData, newItem: AllData) = oldItem == newItem
        }

        private fun getValue(oldItem: AllData): Int {
            return when (oldItem) {
                is AllData.Course -> oldItem.courseName.id
                is AllData.Users -> oldItem.user.id
            }
        }
    }
}