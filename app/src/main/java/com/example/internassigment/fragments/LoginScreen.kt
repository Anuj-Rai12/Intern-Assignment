package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.internassigment.R
import com.example.internassigment.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen:Fragment(R.layout.login_fragment) {
    private lateinit var binding:LoginFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= LoginFragmentBinding.bind(view)
        binding.signInBtn.setOnClickListener {
            val action=LoginScreenDirections.actionLoginScreenToRegistrationScreen()
            findNavController().navigate(action)
        }
        binding.loginBtn.setOnClickListener {
            val action=LoginScreenDirections.actionGlobalStudentDashBoard()
            findNavController().navigate(action)
        }
    }
}