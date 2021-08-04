package com.example.internassigment.recycle

import android.animation.Animator
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.WhyChoose
import com.example.internassigment.databinding.SubjectItemBinding
import com.example.internassigment.databinding.UserDescLayoutBinding
import com.example.internassigment.utils.TAG
import com.example.internassigment.utils.web

sealed class AllViewHolder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    class UserInfo(private val binding: UserDescLayoutBinding) : AllViewHolder(binding) {
        fun bindIt(users: AllData.Users) {
            binding.userTitle.text = users.user.firstname
        }

        fun bindItDash(whyChoose: WhyChoose) {
            binding.userTitle.text = whyChoose.title
            binding.usersDescription.text = whyChoose.description
        }
    }

    class SubjectItem(private val binding: SubjectItemBinding) : AllViewHolder(binding) {
        fun bindIt(subjectInfo: AllData.Course, position: Int, itemClicked: (CourseName) -> Unit) {
            binding.apply {
                CourseName.text = subjectInfo.courseName.courseName
                totalCourse.text =
                    totalCourse.context.getString(
                        R.string.total_weeks,
                        subjectInfo.courseName.week
                    )
                if (isEven(position + 1))
                    setMargins(root)

                myLottieImageView.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        CourseName.hide()
                        totalCourse.hide()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        CourseName.show()
                        totalCourse.show()
                        myLottieImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        getImage(subjectInfo.courseName.courseName)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        Log.i(TAG, "onAnimationCancel: Animation is Cancel")
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                        Log.i(TAG, "onAnimationRepeat: Animation is Pause")
                    }
                })
                myLottieImageView.setAnimation(subjectInfo.courseName.thumbnails)
                binding.root.setOnClickListener {
                    itemClicked(subjectInfo.courseName)
                }
            }
        }

        private fun setMargins(
            view: View,
            left: Int = 0,
            top: Int = 50,
            right: Int = 0,
            bottom: Int = 0
        ) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(left, top, right, bottom)
                view.requestLayout()
            }
        }

        private fun getImage(data: String) = if (data == web) {
            binding.myLottieImageView.setImageResource(IMAGE)
            true
        } else
            false

        private fun isEven(position: Int) = position % 2 == 0
    }
}

//Utils Function to Hide View
fun View.hide() {
    this.isVisible = false
}

//Utils Function to Show View
fun View.show() {
    this.isVisible = true
}

const val IMAGE = R.drawable.hand_image