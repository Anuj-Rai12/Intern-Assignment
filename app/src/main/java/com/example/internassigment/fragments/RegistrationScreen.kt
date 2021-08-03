package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.internassigment.R
import com.example.internassigment.databinding.RegistrationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationScreen : Fragment(R.layout.registration_fragment) {
    private lateinit var binding: RegistrationFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RegistrationFragmentBinding.bind(view)
        binding.registerBtn.setOnClickListener {
            val action = RegistrationScreenDirections.actionGlobalDialog(
                title = "Error!!",
                message = getString(R.string.app_pass_hint)
            )
            findNavController().navigate(action)
        }
        binding.signBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}