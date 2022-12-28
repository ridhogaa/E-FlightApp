package com.c10.finalproject.ui.user.notification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.c10.finalproject.databinding.FragmentNotificationBinding
import com.c10.finalproject.ui.user.history.HistoryAdapter
import com.c10.finalproject.ui.user.home.HomeViewModel
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private val viewModel: NotificationViewModel by activityViewModels()
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeData()
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getNotifications(it)
        }
    }

    private fun initList() {
        binding.apply {
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.notification.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbNotificationList.isVisible = true
                    binding.rvNotification.isVisible = false
                    binding.emptyState.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbNotificationList.isVisible = true
                    binding.rvNotification.isVisible = false
                    binding.emptyState.isVisible = false
                }
                is Resource.Success -> {
                    binding.pbNotificationList.isVisible = false
                    binding.emptyState.isVisible = false
                    binding.rvNotification.isVisible = true
                    viewModel.ticket.observe(viewLifecycleOwner) { list ->
                        binding.rvNotification.adapter = NotificationAdapter(it.data, list)
                    }
                }
                is Resource.Empty -> {
                    binding.pbNotificationList.isVisible = false
                    binding.rvNotification.isVisible = false
                    binding.emptyState.isVisible = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}