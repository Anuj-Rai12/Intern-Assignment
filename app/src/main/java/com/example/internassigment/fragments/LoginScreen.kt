package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internassigment.R
import com.example.internassigment.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen:Fragment(R.layout.login_fragment) {
    private lateinit var binding:LoginFragmentBinding
    private val args:LoginScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= LoginFragmentBinding.bind(view)
        binding.signInBtn.setOnClickListener {
            val action=LoginScreenDirections.actionLoginScreenToRegistrationScreen(args.course)
            findNavController().navigate(action)
        }
        binding.loginBtn.setOnClickListener {
            val action=LoginScreenDirections.actionGlobalStudentDashBoard()
            findNavController().navigate(action)
        }
    }
}