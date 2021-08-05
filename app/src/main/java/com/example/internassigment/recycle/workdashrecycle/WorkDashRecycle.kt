package com.example.internassigment.recycle.workdashrecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.internassigment.data.AllData
import com.example.internassigment.databinding.UserDescLayoutBinding
import com.example.internassigment.recycle.AllViewHolder
import javax.inject.Inject

class WorkDashRecycle @Inject constructor() :
    ListAdapter<AllData, AllViewHolder.UserInfo>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder.UserInfo {
        val binding =
            UserDescLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllViewHolder.UserInfo(binding)
    }

    override fun onBindViewHolder(holder: AllViewHolder.UserInfo, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { whyChoose ->
            when (whyChoose) {
                is AllData.Course -> holder.courseDesc(whyChoose)
                is AllData.Users -> holder.bindItDash(whyChoose)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AllData>() {
            override fun areItemsTheSame(oldItem: AllData, newItem: AllData) =
                getValue(oldItem) == getValue(newItem)

            override fun areContentsTheSame(oldItem: AllData, newItem: AllData) =
                oldItem == newItem
        }

        private fun getValue(allData: AllData): Int {
            return when (allData) {
                is AllData.Course -> allData.courseName.id
                is AllData.Users -> allData.user.id
            }
        }
    }
}