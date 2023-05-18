package com.ernestguevara.openweatherapicollabera.presentation.auth.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ernestguevara.openweatherapicollabera.databinding.FragmentLoginBinding
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthActivity
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthViewModel
import com.ernestguevara.openweatherapicollabera.util.RequestState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnLogin.setOnClickListener {
                login()
            }

            tvRegisterMessage.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
            }
        }

        observeViewModel()
    }

    private fun login() {
        binding.run {
            authViewModel.loginUser(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    private fun observeViewModel() {
        authViewModel.loginData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Toast.makeText(
                    activity,
                    "Login Successful!",
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

        authViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                RequestState.Loading -> (activity as AuthActivity).showLoadingDialog()
                RequestState.Failed,
                RequestState.Finished -> (activity as AuthActivity).dismissLoadingDialog()
                else -> {}
            }
        }
    }
}