package com.example.internassigment.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.internassigment.R
import com.example.internassigment.databinding.WorkshopFragmentBinding
import com.example.internassigment.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkShopFragment : Fragment(R.layout.workshop_fragment) {
    private lateinit var binding: WorkshopFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WorkshopFragmentBinding.bind(view)
        Log.i(TAG, "onViewCreated: Hello from WorkShop")
        binding.root.setOnClickListener {
            val action=WorkShopFragmentDirections.actionWorkShopFragmentToWorkShopDash()
            findNavController().navigate(action)
        }
    }
}