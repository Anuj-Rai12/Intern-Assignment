package com.example.internassigment.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internassigment.R
import com.example.internassigment.databinding.WorkShopDashFragmentBinding
import com.example.internassigment.utils.TAG

class WorkShopDash : Fragment(R.layout.work_shop_dash_fragment) {
    private lateinit var binding: WorkShopDashFragmentBinding
    private val args: WorkShopDashArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WorkShopDashFragmentBinding.bind(view)
        Log.i(TAG, "onViewCreated: ${args.course}")
        binding.applyWorkShop.setOnClickListener {
            val action = WorkShopDashDirections.actionWorkShopDashToLoginScreen()
            findNavController().navigate(action)
        }
    }
}