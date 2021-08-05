package com.example.internassigment.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.databinding.WorkShopDashFragmentBinding
import com.example.internassigment.recycle.workdashrecycle.WorkDashRecycle
import com.example.internassigment.utils.CustomProgress
import com.example.internassigment.utils.MySealed
import com.example.internassigment.viewmodle.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkShopDash : Fragment(R.layout.work_shop_dash_fragment) {
    private lateinit var binding: WorkShopDashFragmentBinding
    private val args: WorkShopDashArgs by navArgs()
    private val myViewModel: MyViewModel by viewModels()
    private val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var dialogFlag: Boolean? = null

    @Inject
    lateinit var workDashRecycle: WorkDashRecycle

    @Inject
    lateinit var customProgress: CustomProgress

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WorkShopDashFragmentBinding.bind(view)
        savedInstanceState?.let {
            dialogFlag = it.getBoolean(DIALOG_ONCE)
        }
        if (dialogFlag == true) {
            applyForWork(args.course)
        }
        /*binding.CoursesImage.setAnimation(args.course.thumbnails)
        val str = "Learn\n" + args.title
        binding.courseTitle.text = str
        binding.whyThisCourse.text = "Why to $str?"*/
        setRecyclerview()
        /*args.course.whyChoose*/
        setData()
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
                        applyForWork(name)
                        return@setOnClickListener
                    }
                    dir(23, courseName = name)
                }
            }
        }
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    private fun applyForWork(courseName: CourseName) {
        dialogFlag = true
        myViewModel.applyForWork(courseName).observe(viewLifecycleOwner) {
            when (it) {
                is MySealed.Error -> {
                    dialogFlag = false
                    hideLoading()
                    dir(
                        title = "Error",
                        message = it.exception?.localizedMessage ?: "UnWanted Error :("
                    )
                }
                is MySealed.Loading -> {
                    showLoading(it.data as String)
                }
                is MySealed.Success -> {
                    dialogFlag = false
                    hideLoading()
                    dir(1)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    private fun dir(
        choose: Int = 0,
        title: String = "",
        message: String = "",
        courseName: CourseName? = null
    ) {
        val action = when (choose) {
            0 -> WorkShopDashDirections.actionGlobalDialog(title, message)
            1 -> WorkShopDashDirections.actionGlobalStudentDashBoard()
            else -> WorkShopDashDirections.actionWorkShopDashToLoginScreen(courseName!!)
        }
        findNavController().navigate(action)
    }

    private fun setRecyclerview() {
        binding.keyRecycle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workDashRecycle
        }
    }

    private fun setData() {
        val allData = mutableListOf<AllData>()
        val user = User(
            0,
            firstname = args.title,
            lastname = args.title,
            email = "",
            password = "",
            phone = ""
        )
        allData.add(AllData.Course(args.course))
        allData.add(AllData.Users(user))
        args.course.whyChoose?.filterIndexed { index, whyChoose ->
            val userDesc = User(
                id = index,
                firstname = whyChoose.title,
                lastname = whyChoose.description,
                email = "",
                password = "",
                phone = ""
            )
            allData.add(AllData.Users(userDesc))
            return@filterIndexed true
        }
        workDashRecycle.submitList(allData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dialogFlag?.let {
            outState.putBoolean(DIALOG_ONCE, it)
        }
    }
}

private const val DIALOG_ONCE = "DIALOG_FLAG"