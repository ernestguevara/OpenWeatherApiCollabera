package com.ernestguevara.openweatherapicollabera.presentation.auth.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ernestguevara.openweatherapicollabera.databinding.FragmentSignupBinding
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthActivity
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnSignup.setOnClickListener {
                signup()
            }

            tvSignupMessage.setOnClickListener { navigateToLogin() }
            ivBack.setOnClickListener { navigateToLogin() }
        }

        observeViewModel()
    }

    private fun signup() {
        binding.run {
            authViewModel.signupUser(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    private fun observeViewModel() {
        authViewModel.signupData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Toast.makeText(
                    activity,
                    "Signup Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                (activity as AuthActivity).openMainActivity()
            }
        }

        authViewModel.errorData.observe(viewLifecycleOwner) { result ->
            Toast.makeText(
                activity,
                result,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
    }
}