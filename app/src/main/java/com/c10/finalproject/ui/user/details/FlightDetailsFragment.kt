package com.c10.finalproject.ui.user.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.tickets.model.GetTicketByIdResponse
import com.c10.finalproject.databinding.FragmentFlightDetailsBinding
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.c10.finalproject.ui.user.home.HomeViewModel
import com.c10.finalproject.utils.Utils
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : Fragment() {

    private val viewModel: FlightDetailsViewModel by activityViewModels()
    private var _binding: FragmentFlightDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(arguments?.getInt("ID_TICKET")!!)
        observeData()
        btnCancel()
    }

    private fun btnCancel() {
        binding.btnCancel.setOnClickListener {

        }
    }

    private fun observeData() {
        viewModel.detailResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Error -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    setView(it.payload)
                }
                else -> {}
            }
        }
    }

    private fun setView(gtbir: GetTicketByIdResponse?) {
        gtbir?.data?.let {
            binding.apply {
                airplaneName.text = it.airplaneName
                from.text = it.origin
                to.text = it.destination
                fromTime.text = it.departureTime?.substring(11, 16)
                toTime.text = it.arrivalTime?.substring(11, 16)
                category.text = it.category
                price.text = Utils.formatRupiah(it.price!!)
                etDepartureDate.setText(it.departureTime?.substring(0, 10))
                if (it.category.equals("one_way", true)) {
                    tilReturnDate.visibility = View.GONE
                } else {
                    tilReturnDate.visibility = View.VISIBLE
                    etReturnDate.setText(it.returnTime?.toString()?.substring(0, 10))
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbDetailList.isVisible = isLoading
        binding.constraintLayout.isVisible = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}