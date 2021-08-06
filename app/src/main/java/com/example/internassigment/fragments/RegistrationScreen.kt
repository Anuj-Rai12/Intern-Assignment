package com.example.internassigment.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internassigment.R
import com.example.internassigment.data.User
import com.example.internassigment.databinding.RegistrationFragmentBinding
import com.example.internassigment.utils.*
import com.example.internassigment.viewmodle.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationScreen : Fragment(R.layout.registration_fragment) {
    private lateinit var binding: RegistrationFragmentBinding
    private val args: RegistrationScreenArgs by navArgs()
    private val myViewModel: MyViewModel by viewModels()

    @Inject
    lateinit var customProgress: CustomProgress

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RegistrationFragmentBinding.bind(view)
        myViewModel.mutableStateFlow.value?.let {
            createUserAccount(it)
        }
        binding.registerBtn.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.emailAddress.text.toString()
            val pass = binding.password.text.toString()
            var phone = binding.phone.text.toString()
            if (checkFieldValue(firstName)
                || checkFieldValue(lastName) ||
                checkFieldValue(email) ||
                checkFieldValue(pass) ||
                checkFieldValue(phone)
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
            phone = binding.countryCode.selectedCountryCodeWithPlus + phone
            if (!isValidPhone(phone)) {
                Snackbar.make(requireView(), getString(R.string.wrong_phone), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            User(
                id = 0,
                firstname = firstName,
                lastname = lastName,
                phone = phone,
                email = email,
                password = pass
            ).also { user ->
                createUserAccount(user)
            }
        }
        binding.signBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun hideLoading() = customProgress.hideLoading()
    private fun showLoading(string: String) = customProgress.showLoading(requireActivity(), string)

    private fun createUserAccount(user: User) {
        myViewModel.mutableStateFlow.value=user
        myViewModel.createUsers(user = user, courseName = args.course).observe(viewLifecycleOwner) {
            when (it) {
                is MySealed.Error -> {
                    hideLoading()
                    myViewModel.mutableStateFlow.value=null
                    dir(message = it.exception?.localizedMessage ?: "UnWanted Error")
                }
                is MySealed.Loading -> {
                    showLoading(it.data as String)
                }
                is MySealed.Success -> {
                    hideLoading()
                    setUpDir(email = user.email,password = user.password)
                }
            }
        }
    }

    private fun setUpDir(email:String,password:String) {
        myViewModel.alwaysRememberMe(UserStore(email,password,flag = false))
        myViewModel.mutableStateFlow.value=null
        dir(choose = 3)
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    private fun dir(choose: Int = 0, message: String = "", title: String = "Error!!") {
        val action = when (choose) {
            0 -> {
                RegistrationScreenDirections.actionGlobalDialog(
                    title = title,
                    message = message
                )
            }
            else -> {
                RegistrationScreenDirections.actionGlobalStudentDashBoard("don't_loading_screen")
            }
        }
        findNavController().navigate(action)
    }
}