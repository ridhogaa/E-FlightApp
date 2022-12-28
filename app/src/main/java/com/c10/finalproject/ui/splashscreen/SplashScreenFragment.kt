package com.c10.finalproject.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.ui.AdminActivity
import com.c10.finalproject.ui.MainActivity
import com.c10.finalproject.ui.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private val viewModel: SplashScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        loadingScreen()
        return view
    }

    private fun loadingScreen() {
        Handler(Looper.getMainLooper()).postDelayed(this::isLogin, 2000)
    }

    private fun isLogin() {
        lifecycleScope.launchWhenCreated {
            viewModel.getToken().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    if (it.equals(TOKEN_ADMIN, true)) {
                        startActivity(Intent(requireContext(), AdminActivity::class.java))
                        activity?.finish()
                    } else {
                        startActivity(Intent(requireContext(), UserActivity::class.java))
                        activity?.finish()
                    }
                } else {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }

    companion object {
        private const val TOKEN_ADMIN =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywibmFtZSI6IkFkbWluIiwiZW1haWwiOiJhZG1pbkBiaW5hci5jby5pZCIsInJvbGUiOnsiaWQiOjIsIm5hbWUiOiJBRE1JTiJ9LCJpYXQiOjE2NzE4MDk5NzZ9.yEZQkQKTL9TksWZNnESTXsukOjd4VKnukmKt6pCOCIA"
    }
}