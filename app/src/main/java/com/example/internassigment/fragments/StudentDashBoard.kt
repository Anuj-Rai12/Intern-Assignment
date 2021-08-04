package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.databinding.StudentDashboardFramgentBinding
import com.example.internassigment.recycle.WorkShopRecycleView
import com.example.internassigment.utils.CustomProgress
import com.example.internassigment.utils.MySealed
import com.example.internassigment.viewmodle.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentDashBoard : Fragment(R.layout.student_dashboard_framgent) {
    private lateinit var binding: StudentDashboardFramgentBinding
    private val myViewModel: MyViewModel by viewModels()
    private var workShopRecycleView: WorkShopRecycleView? = null

    @Inject
    lateinit var customProgress: CustomProgress

    companion object {
        var Flag: Boolean? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StudentDashboardFramgentBinding.bind(view)
        setUpRecycleView()
        getData()
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    private fun getData() {
        myViewModel.getAllUsers().observe(viewLifecycleOwner) {
            when (it) {
                is MySealed.Error -> {
                    hideLoading()
                    Flag=true
                    dir(
                        title = "Error",
                        message = it.exception?.localizedMessage ?: "UnWanted Error"
                    )
                }
                is MySealed.Loading -> showLoading(it.data as String)
                is MySealed.Success -> {
                    Flag=true
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
        workShopRecycleView?.submitList(allData)
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    private fun setUpRecycleView() {
        binding.studentSelectedCourse.apply {
            workShopRecycleView = WorkShopRecycleView { CourseName ->
                val message =
                    "You have Successfully Applied for ${CourseName.courseName}\n Duration : ${CourseName.week} Week long."
                dir(title = "Applied", message = message)
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

    private fun dir(title: String, message: String) {
        val action = StudentDashBoardDirections.actionGlobalDialog(
            title = title,
            message = message
        )
        findNavController().navigate(action)
    }
}