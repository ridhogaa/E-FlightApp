package com.c10.finalproject.ui.user.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c10.finalproject.databinding.FragmentWishlistBinding
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private val viewModel: WishlistViewModel by activityViewModels()
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken().observe(viewLifecycleOwner) {
            viewModel.getWishlists(it)
        }
        initList()
        observeData()
    }

    private fun initList() {
        binding.apply {
            rvWishlist.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.wishlist.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbWishlistList.isVisible = true
                    binding.rvWishlist.isVisible = false
                    binding.emptyState.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbWishlistList.isVisible = true
                    binding.rvWishlist.isVisible = false
                    binding.emptyState.isVisible = false
                }
                is Resource.Success -> {
                    binding.pbWishlistList.isVisible = false
                    binding.rvWishlist.isVisible = true
                    binding.emptyState.isVisible = false
                    viewModel.ticket.observe(viewLifecycleOwner) { list ->
                        binding.rvWishlist.adapter = WishlistAdapter(list)
                    }
                }
                else -> {
                    binding.pbWishlistList.isVisible = false
                    binding.rvWishlist.isVisible = false
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