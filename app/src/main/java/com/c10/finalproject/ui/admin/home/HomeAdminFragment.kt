package com.c10.finalproject.ui.admin.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.databinding.FragmentHomeAdminBinding
import com.c10.finalproject.wrapper.Resource

class HomeAdminFragment : Fragment() {

    private val viewModel: HomeAdminViewModel by activityViewModels()
    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.getHomeAdminTicket()
        viewModel.homeAdminTicket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Error -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    setHomeRecyclerView(it.payload)
                }
                else -> {
                    setLoadingState(true)
                }
            }
        }
    }

    private fun setHomeRecyclerView(ticket: List<Data>?) {
        val adapter = HomeAdminAdapter()
        adapter.submitList(ticket)

        binding.apply {
            rvListFlight.layoutManager = LinearLayoutManager(requireContext())
            rvListFlight.adapter = adapter
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbHomeAdminList.isVisible = isLoading
        binding.rvListFlight.isVisible = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
