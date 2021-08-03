package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.internassigment.R
import com.example.internassigment.databinding.WorkShopDashFragmentBinding

class WorkShopDash:Fragment(R.layout.work_shop_dash_fragment) {
    private lateinit var binding:WorkShopDashFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= WorkShopDashFragmentBinding.bind(view)
        binding.applyWorkShop.setOnClickListener {
            val action=WorkShopDashDirections.actionWorkShopDashToLoginScreen()
            findNavController().navigate(action)
        }
    }
}