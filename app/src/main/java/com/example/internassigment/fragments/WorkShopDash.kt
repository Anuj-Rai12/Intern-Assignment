package com.example.internassigment.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internassigment.R
import com.example.internassigment.data.CourseName
import com.example.internassigment.databinding.WorkShopDashFragmentBinding
import com.example.internassigment.recycle.workdashrecycle.WorkDashRecycle
import com.example.internassigment.utils.TAG
import com.example.internassigment.viewmodle.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkShopDash  : Fragment(R.layout.work_shop_dash_fragment) {
    private lateinit var binding: WorkShopDashFragmentBinding
    private val args: WorkShopDashArgs by navArgs()
    private val myViewModel: MyViewModel by viewModels()
    private val firebaseUser: FirebaseUser?=FirebaseAuth.getInstance().currentUser
    @Inject
    lateinit var workDashRecycle: WorkDashRecycle

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WorkShopDashFragmentBinding.bind(view)
        Log.i(TAG, "onViewCreated: ${args.course}")
        binding.CoursesImage.setAnimation(args.course.thumbnails)
        val str = "Learn\n" + args.title
        binding.courseTitle.text = str
        binding.whyThisCourse.text = "Why to $str?"
        setRecyclerview()
        workDashRecycle.submitList(args.course.whyChoose)
        binding.applyWorkShop.setOnClickListener {
            args.course.also { courseName ->
                CourseName(
                    id = courseName.id,
                    courseName = courseName.courseName,
                    thumbnails = courseName.thumbnails,
                    week = courseName.week,
                    courseSelected = true,
                    whyChoose = courseName.whyChoose
                ).also { name ->
                    firebaseUser?.let {
                        //ADD It
                        applyForWork(name)
                        return@setOnClickListener
                    }
                    dir(name)
                }
            }
        }
    }

    private fun applyForWork(courseName: CourseName) {
        Log.i(TAG, "applyForWork: $courseName")
        //Apply for work for exiting User
    }

    private fun dir(courseName: CourseName) {
        val action = WorkShopDashDirections.actionWorkShopDashToLoginScreen(courseName)
        findNavController().navigate(action)
    }

    private fun setRecyclerview() {
        binding.keyRecycle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workDashRecycle
        }
    }
}