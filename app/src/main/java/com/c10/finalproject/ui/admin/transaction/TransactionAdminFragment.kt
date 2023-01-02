package com.c10.finalproject.ui.admin.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.data.remote.tickets.model.histories.DataUsers
import com.c10.finalproject.databinding.FragmentAdminTransactionBinding
import com.c10.finalproject.wrapper.Resource

class TransactionAdminFragment : Fragment() {

    private val viewModel: TransactionAdminViewModel by activityViewModels()
    private var _binding: FragmentAdminTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {

        viewModel.getTransactionHistories()

        viewModel.ticketResult.observe(viewLifecycleOwner) {
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
                    binding.transactionEmptyState.visibility = View.VISIBLE

                }
            }
        }

    }

    private fun setHomeRecyclerView(ticket: List<Data>?) {
        val adapter = TransactionAdminAdapter()
        adapter.submitList(ticket)

        binding.apply {
            rvListTransaction.layoutManager = LinearLayoutManager(requireContext())
            rvListTransaction.adapter = adapter
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbTransactionAdminList.isVisible = isLoading
        binding.rvListTransaction.isVisible = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}