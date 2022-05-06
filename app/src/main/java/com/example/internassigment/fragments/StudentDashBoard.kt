package com.example.internassigment.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internassigment.R
import com.example.internassigment.data.AllData
import com.example.internassigment.databinding.StudentDashboardFramgentBinding
import com.example.internassigment.recycle.WorkShopRecycleView
import com.example.internassigment.utils.CustomProgress
import com.example.internassigment.utils.MySealed
import com.example.internassigment.utils.TAG
import com.example.internassigment.viewmodle.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StudentDashBoard : Fragment(R.layout.student_dashboard_framgent) {
    private lateinit var binding: StudentDashboardFramgentBinding
    private val myViewModel: MyViewModel by viewModels()
    private var workShopRecycleView: WorkShopRecycleView? = null
    private val args: StudentDashBoardArgs by navArgs()

    @Inject
    lateinit var customProgress: CustomProgress

    companion object {
        var Flag: Boolean? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StudentDashboardFramgentBinding.bind(view)
        lifecycleScope.launch {
            myViewModel.rememberMe.asFlow().collect {
                Log.i(TAG, "onViewCreated: ${it.email} and Password -> ${it.password}")
                getData(it.email, it.password)
            }
        }
        setUpRecycleView()
        setHasOptionsMenu(true)
        binding.addFloatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_studentDashBoard_to_workShopFragment)
        }
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    override fun onResume() {
        super.onResume()
        hideLoading()
    }

    private fun getData(email: String, password: String) {
        myViewModel.getAllUsers(email, password).observe(viewLifecycleOwner) {
            when (it) {
                is MySealed.Error -> {
                    if (args.regflag.isNullOrEmpty())
                        hideLoading()
                    Flag = true
                    dir(
                        title = "Error",
                        message = it.exception?.localizedMessage ?: "UnWanted Error"
                    )
                }
                is MySealed.Loading -> {
                    if (args.regflag.isNullOrEmpty())
                        showLoading(it.data as String)
                }
                is MySealed.Success -> {
                    if (args.regflag.isNullOrEmpty())
                        hideLoading()
                    Flag = true
                    Log.i(TAG, "getData: Hit to Success iN Student")
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
                Log.i(TAG, "setUpRecycleView: $CourseName")
                val message =
                    "You have Successfully Applied for ${CourseName.courseName}\nDuration : ${CourseName.week} Week long."
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
        val log = menu.findItem(R.id.logout_btn)
        log.setOnMenuItemClickListener {
            val action = StudentDashBoardDirections.actionGlobalCustomLog(
                title = "Log Out!!",
                getString(R.string.Log_out_msg)
            )
            findNavController().navigate(action)
            return@setOnMenuItemClickListener true
        }
    }
}