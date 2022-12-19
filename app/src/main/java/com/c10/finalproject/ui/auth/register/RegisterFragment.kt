package com.c10.finalproject.ui.auth.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.auth.model.RegisterBody
import com.c10.finalproject.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register()
        haveAccount()
    }

    private fun register() {
        // TODO : add logic register wait for api
        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            lifecycleScope.launchWhenResumed {
                if (job.isActive) job.cancel()
                job = launch {
                    viewModel.register(RegisterBody(email, name, password)).collect {
                        it.onSuccess { response ->
                            Toast.makeText(
                                requireContext(),
                                response.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        it.onFailure { responseFailure ->
                            Toast.makeText(
                                requireContext(),
                                responseFailure.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.apply {
                                etEmail.text?.clear()
                                etName.text?.clear()
                                etPassword.text?.clear()
                            }
                        }
                    }
                }
            }

        }
    }

    private fun haveAccount() {
        binding.tvSigninHere.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}