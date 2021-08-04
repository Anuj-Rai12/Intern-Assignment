package com.example.internassigment.recycle.workdashrecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.internassigment.data.WhyChoose
import com.example.internassigment.databinding.UserDescLayoutBinding
import com.example.internassigment.recycle.AllViewHolder
import javax.inject.Inject

class WorkDashRecycle @Inject constructor() : ListAdapter<WhyChoose, AllViewHolder.UserInfo>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder.UserInfo {
        val binding =
            UserDescLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllViewHolder.UserInfo(binding)
    }

    override fun onBindViewHolder(holder: AllViewHolder.UserInfo, position: Int) {
        val currentItem=getItem(position)
        currentItem?.let {whyChoose ->
            holder.bindItDash(whyChoose = whyChoose)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WhyChoose>() {
            override fun areItemsTheSame(oldItem: WhyChoose, newItem: WhyChoose) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: WhyChoose, newItem: WhyChoose) =
                oldItem == newItem
        }
    }
}