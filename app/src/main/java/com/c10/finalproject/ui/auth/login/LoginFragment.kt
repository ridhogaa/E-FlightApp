package com.c10.finalproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.auth.model.LoginBody
import com.c10.finalproject.databinding.FragmentLoginBinding
import com.c10.finalproject.ui.UserActivity
import com.c10.finalproject.ui.AdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        dontHaveAccount()
//        isLogin()
    }

    private fun login() {
        // TODO : add logic login wait for api
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            lifecycleScope.launchWhenResumed {
                if (job.isActive) job.cancel()
                job = launch {
                    viewModel.login(LoginBody(email, password)).collect() {
                        it.onSuccess { response ->
                            viewModel.setToken(response.accessToken.toString())
                            if (response.role.equals("admin", ignoreCase = true)) {
                                Toast.makeText(
                                    requireContext(),
                                    response.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(requireContext(), AdminActivity::class.java))
                                //activity?.finish()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    response.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(requireContext(), UserActivity::class.java))
                                //activity?.finish()
                            }
                        }
                        it.onFailure { responseFailure ->
                            Toast.makeText(
                                requireContext(),
                                responseFailure.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.apply {
                                etEmail.text?.clear()
                                etPassword.text?.clear()
                            }
                        }
                    }
                }
            }
        }
    }

//    private fun isLogin() {
//        viewModel.getToken().observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                if (it.equals(TOKEN_ADMIN, true)) {
//                    navigateToHomeAdmin()
//                } else {
//                    navigateToHomeUser()
//                }
//            } else {
//
//            }
//        }
//    }

//    private fun navigateToHomeAdmin() {
//        val intent = Intent(requireContext(), AdminActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        }
//        startActivity(intent)
//        activity?.finish()
//    }
//
//    private fun navigateToHomeUser() {
//        val intent = Intent(requireContext(), UserActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        }
//        startActivity(intent)
//        activity?.finish()
//    }

    private fun dontHaveAccount() {
        binding.tvSignupHere.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}