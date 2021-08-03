package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.internassigment.R
import com.example.internassigment.databinding.StudentDashboardFramgentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentDashBoard:Fragment(R.layout.student_dashboard_framgent) {
    private lateinit var binding:StudentDashboardFramgentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= StudentDashboardFramgentBinding.bind(view)
    }
}