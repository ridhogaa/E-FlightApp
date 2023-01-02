package com.c10.finalproject.ui.user.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.databinding.FragmentHistoryBinding
import com.c10.finalproject.ui.user.search.SearchAdapter
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by activityViewModels()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getOrder(it)
        }
        initList()
        observeData()
    }

    private fun initList() {
        binding.apply {
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.history.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbHistoryList.isVisible = true
                    binding.rvHistory.isVisible = false
                    binding.emptyState.isVisible = false
                    binding.stateError.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbHistoryList.isVisible = false
                    binding.rvHistory.isVisible = false
                    binding.emptyState.isVisible = false
                    binding.stateError.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbHistoryList.isVisible = false
                    binding.emptyState.isVisible = false
                    binding.rvHistory.isVisible = true
                    binding.stateError.isVisible = false
                    viewModel.ticket.observe(viewLifecycleOwner) { list ->
                        binding.rvHistory.adapter = HistoryAdapter(list)
                    }
                }
                else -> {
                    binding.pbHistoryList.isVisible = false
                    binding.rvHistory.isVisible = false
                    binding.emptyState.isVisible = true
                    binding.stateError.isVisible = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}