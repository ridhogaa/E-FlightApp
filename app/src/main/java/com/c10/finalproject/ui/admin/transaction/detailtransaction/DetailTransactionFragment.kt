package com.c10.finalproject.ui.admin.transaction.detailtransaction

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.databinding.FragmentDetailTransactionBinding
import com.c10.finalproject.utils.Utils
import com.c10.finalproject.wrapper.Resource

class DetailTransactionFragment : Fragment() {

    private val viewModel: DetailTransactionViewModel by activityViewModels()
    private var _binding: FragmentDetailTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe detail
        getDetailID()

        // observe
        observeDataSetView()


    }

    private fun getDetailID() {
        viewModel.getDetail(arguments?.getInt("ID_TICKET")!!)
    }

    private fun observeDataSetView() {
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
                tvFligtCodeDetail.text = it.airplaneName
                tvTypeItemTrans.text = it.category
                tvFromDetail.text = it.origin
                tvToDetail.text = it.destination
                tvDepartureDetail.text = it.departureTime?.substring(11, 16)
                tvLandingDetail.text = it.arrivalTime?.substring(11, 16)
                tvTotalDetail.text = Utils.formatRupiah(it.price!!)
                etDepartureDateDetail.setText(it.departureTime?.substring(0, 10))

                if (it.category.equals("one_way", true)) {
                    tilReturnDateDetail.visibility = View.GONE
                    tvDeparture2Detail.visibility = View.GONE
                    tvLanding2Detail.visibility = View.GONE
                    imageView22.visibility = View.GONE
                    tvFrom2Detail.visibility = View.GONE
                    tvTo2Detail.visibility = View.GONE

                } else {
                    tilReturnDateDetail.visibility = View.VISIBLE
                    tvDeparture2Detail.visibility = View.VISIBLE
                    tvLanding2Detail.visibility = View.VISIBLE
                    imageView22.visibility = View.VISIBLE
                    tvFrom2Detail.visibility = View.VISIBLE
                    tvTo2Detail.visibility = View.VISIBLE

                    // date
                    etReturnDateDetail.setText(it.returnTime?.toString()?.substring(0, 10))

                    // time 2
                    tvDeparture2Detail.setText(it.returnTime?.toString()?.substring(11, 16))
                    tvLanding2Detail.setText(it.arrival2Time?.toString()?.substring(11, 16))

                    // destination
                    tvFrom2Detail.text = it.destination
                    tvTo2Detail.text = it.origin
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbDetailTransactionAdmin.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}