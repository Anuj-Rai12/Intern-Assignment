package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internassigment.R
import com.example.internassigment.data.User
import com.example.internassigment.databinding.LoginFragmentBinding
import com.example.internassigment.utils.*
import com.example.internassigment.viewmodle.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.login_fragment) {
    private lateinit var binding: LoginFragmentBinding
    private val args: LoginScreenArgs by navArgs()
    private val myViewModel: MyViewModel by viewModels()

    @Inject
    lateinit var customProgress: CustomProgress

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        myViewModel.mutableStateFlow.value?.let {
            signIn(it.email, it.password)
        }
        myViewModel.rememberMe.observe(viewLifecycleOwner) {
            if (it.flag) {
                binding.emailText.setText(it.email)
                binding.passwordText.setText(it.password)
                binding.remeberme.isChecked = it.flag
            }
        }
        binding.signInBtn.setOnClickListener {
            val action = LoginScreenDirections.actionLoginScreenToRegistrationScreen(args.course)
            findNavController().navigate(action)
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val pass = binding.passwordText.text.toString()
            if (checkFieldValue(email) ||
                checkFieldValue(pass)
            ) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.wrong_detail),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!isValidEmail(email)) {
                Snackbar.make(requireView(), getString(R.string.wrong_email), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!isValidPassword(pass)) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.wrong_password),
                    Snackbar.LENGTH_LONG
                ).setAction("INFO") {
                    dir(message = msg(), title = "Good Password :)")
                }.show()
                return@setOnClickListener
            }
            signIn(email, pass)
        }
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    private fun signIn(email: String, pass: String) {
        myViewModel.mutableStateFlow.value = User(
            id = 0,
            email = email,
            password = pass,
            phone = "",
            firstname = "",
            lastname = ""
        )
        myViewModel.signInUsers(email, password = pass, courseName = args.course)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is MySealed.Error -> {
                        hideLoading()
                        myViewModel.mutableStateFlow.value = null
                        dir(
                            message = it.exception?.localizedMessage ?: "UnWanted Error",
                            title = "Error"
                        )
                    }
                    is MySealed.Loading -> {
                        showLoading(it.data as String)
                    }
                    is MySealed.Success -> {
                        hideLoading()
                        rememberMe(email, pass)
                    }
                }
            }
    }

    private fun rememberMe(email: String, pass: String) {
        val flag = binding.remeberme.isChecked
        if (flag) {
            UserStore(email = email, password = pass, flag = flag).also { userStore ->
                myViewModel.alwaysRememberMe(userStore)
            }
            myViewModel.mutableStateFlow.value = null
            dir(3)
        }
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    private fun dir(choose: Int = 0, message: String = "", title: String = "") {
        val action = when (choose) {
            0 -> LoginScreenDirections.actionGlobalDialog(title, message)
            else -> LoginScreenDirections.actionGlobalStudentDashBoard()
        }
        findNavController().navigate(action)
    }
}