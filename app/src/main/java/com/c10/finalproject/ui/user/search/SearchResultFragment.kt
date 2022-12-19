package com.c10.finalproject.ui.user.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.databinding.FragmentSearchResultBinding
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private val viewModel: SearchResultViewModel by activityViewModels()
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewModel.getSearchResult(
            arguments?.getString("FROM")!!,
            arguments?.getString("TO")!!,
            arguments?.getString("CATEGORY")!!,
            arguments?.getString("DEPARTURE")!!,
            arguments?.getString("RETURN")
        )
        observeData()
    }

    private fun initList() {
        binding.apply {
            rvSearch.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                    binding.emptyState.visibility = View.GONE
                }
                is Resource.Error -> {
                    setLoadingState(true)
                    binding.emptyState.visibility = View.GONE
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    binding.emptyState.visibility = View.GONE
                    binding.rvSearch.adapter = SearchAdapter(it.data)
                }
                else -> {
                    setLoadingState(true)
                    binding.emptyState.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbHomeList.isVisible = isLoading
        binding.rvSearch.isVisible = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}