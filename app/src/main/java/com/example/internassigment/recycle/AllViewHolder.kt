package com.example.internassigment.recycle

import android.animation.Animator
import android.annotation.SuppressLint
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
import com.example.internassigment.databinding.SubjectItemBinding
import com.example.internassigment.databinding.UserDescLayoutBinding
import com.example.internassigment.utils.TAG

sealed class AllViewHolder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    class UserInfo(private val binding: UserDescLayoutBinding) : AllViewHolder(binding) {
        @SuppressLint("SetTextI18n")
        fun bindIt(users: AllData.Users) {
            binding.userTitle.text = "Hey ${users.user.firstname},"
            if (users.user.firstname != "Friend") {
                binding.usersDescription.text =
                    binding.usersDescription.context.getString(R.string.another_user_desc)
            }
            Log.i(TAG, "bindIt: Password ->${users.user.password}\n Email -> ${users.user.email}")
        }

        @SuppressLint("SetTextI18n")
        fun bindItDash(whyChoose: AllData.Users) {
            if (whyChoose.user.firstname == whyChoose.user.lastname) {
                val str = "Learn\n${whyChoose.user.firstname}"
                binding.userTitle.text = "Why to $str?"
                binding.usersDescription.hide()
            } else {
                binding.userTitle.text = whyChoose.user.firstname
                binding.usersDescription.text = whyChoose.user.lastname
            }
        }
        @SuppressLint("SetTextI18n")
        fun courseDesc(whyChoose: AllData.Course) {
            binding.userTitle.text = "Learn\n${whyChoose.courseName.courseName}."
            binding.usersDescription.hide()
            binding.CoursesImage.show()
            binding.CoursesImage.setAnimation(whyChoose.courseName.thumbnails)
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
