package com.example.internassigment.fragments

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.databinding.WorkshopFragmentBinding
import com.example.internassigment.recycle.WorkShopRecycleView
import com.example.internassigment.utils.CustomProgress
import com.example.internassigment.utils.MySealed
import com.example.internassigment.utils.TAG
import com.example.internassigment.viewmodle.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkShopFragment : Fragment(R.layout.workshop_fragment) {
    private val myViewModel: MyViewModel by viewModels()
    private lateinit var binding: WorkshopFragmentBinding
    private var workShopRecycleView: WorkShopRecycleView? = null

    @Inject
    lateinit var customProgress: CustomProgress
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WorkshopFragmentBinding.bind(view)
        if (myViewModel.orientationFlag == true) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
            Log.i(TAG, "onViewCreated: Initial Flag value ${StudentDashBoard.Flag}")
        }
        if (StudentDashBoard.Flag != null || FirebaseAuth.getInstance().currentUser == null) {
            setUpRecycleView()
            getAllWorkShop()
        }
    }

    private fun setUpRecycleView() {
        binding.allWorkShops.apply {
            workShopRecycleView = WorkShopRecycleView { CourseName ->
                dir(2, courseName = CourseName)
            }
            val courseLayoutManager = GridLayoutManager(activity, 2)
            courseLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (workShopRecycleView?.currentList?.get(position)!!) {
                        is AllData.Course -> 1 // return's Span Count Layout i.e 2 because to display Single layout
                        is AllData.Users -> 2 // return's Span Count Layout i.e 2 because to display Single layout
                    }
                }
            }
            adapter = workShopRecycleView
            layoutManager = courseLayoutManager
        }
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    private fun getAllWorkShop() {
        myViewModel.getAllCourse().observe(viewLifecycleOwner) {
            when (it) {
                is MySealed.Error -> {
                    hideLoading()
                    dir(message = it.exception?.localizedMessage ?: "UnWanted Error :(")
                }
                is MySealed.Loading -> {
                    Log.i(TAG, "getAllWorkShop: Activated ${StudentDashBoard.Flag}")
                    showLoading(it.data as String)
                }
                is MySealed.Success -> {
                    hideLoading()
                    val sqlData = it.data as MutableList<*>
                    setData(sqlData)
                }
            }
        }
    }

    private fun setData(sqlData: MutableList<*>) {
        val allData = mutableListOf<AllData>()
        sqlData.forEach {
            val all = it as AllData
            allData.add(all)
        }
        when (val op = allData.first()) {
            is AllData.Course -> Log.i(TAG, "setData: all data")
            is AllData.Users -> {
                if (op.user.phone.toBoolean()) {
                    change()
                    return
                }
            }
        }
        workShopRecycleView?.submitList(allData)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun change() {
        myViewModel.orientationFlag = true
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "onViewCreated: Screen Portrait mode ")
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            Log.i(TAG, "onViewCreated: Screen LandScape Mode")
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }


    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    private fun dir(
        choose: Int = 0,
        title: String = "Error",
        message: String = "",
        courseName: CourseName? = null
    ) {
        val action = when (choose) {
            0 -> WorkShopDashDirections.actionGlobalDialog(title, message)
            else -> WorkShopFragmentDirections.actionWorkShopFragmentToWorkShopDash(
                title = courseName?.courseName!!,
                course = courseName
            )
        }
        findNavController().navigate(action)
    }
}