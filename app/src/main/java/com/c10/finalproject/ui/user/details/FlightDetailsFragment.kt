package com.c10.finalproject.ui.user.details

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.data.local.database.entity.WishlistEntity
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.databinding.FragmentFlightDetailsBinding
import com.c10.finalproject.ui.user.transaction.BottomSheetTransactionFragment
import com.c10.finalproject.utils.Utils
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : Fragment() {

    private val viewModel: FlightDetailsViewModel by activityViewModels()
    private var _binding: FragmentFlightDetailsBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private lateinit var wishlist: WishlistEntity

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
        viewModel.getId().observe(viewLifecycleOwner) {
            observeData(it)
        }
        fabFav()
        btnCancel()
        btnConfirm()
    }

    private fun btnConfirm() {
        binding.btnConfirm.setOnClickListener {
            val bottomSheet = BottomSheetTransactionFragment()
            bottomSheet.arguments = bundle
            bottomSheet.show(
                childFragmentManager,
                "BottomSheet"
            )
        }
    }

    private fun fabFav() {
        binding.fabFav.setOnClickListener {
            viewModel.addFavorite(wishlist)
            fab(false)
        }
        binding.fabFav2.setOnClickListener {
            viewModel.deleteFavorite(wishlist)
            fab(true)
        }
    }

    private fun fab(flag: Boolean) {
        binding.fabFav.isVisible = flag
        binding.fabFav2.isVisible = !flag
    }

    private fun checkFav(ticketId: Int, userId: Int) {
        viewModel.isFavorite(ticketId, userId)
        viewModel.isFav.observe(viewLifecycleOwner) {
            if (it == true) {
                fab(false)
            } else {
                fab(true)
            }
        }
    }

    private fun btnCancel() {
        binding.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Message Dialog")
            builder.setMessage("Are you sure you want to return to the home page?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                findNavController().navigate(R.id.homeFragment)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
    }

    private fun observeData(userId: Int) {
        viewModel.detailResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDetailList.isVisible = true
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbDetailList.isVisible = false
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbDetailList.isVisible = false
                    binding.constraintLayout.isVisible = true
                    binding.stateError.isVisible = false
                    setView(it.payload)
                    checkFav(it.payload?.data?.id!!, userId)
                    wishlist = WishlistEntity(
                        ticketId = it.payload.data.id,
                        userId = userId,
                        airplaneName = it.payload.data.airplaneName,
                        departureTime = it.payload.data.departureTime,
                        arrivalTime = it.payload.data.arrivalTime,
                        returnTime = it.payload.data.returnTime.toString(),
                        arrival2Time = it.payload.data.arrival2Time.toString(),
                        price = it.payload.data.price,
                        category = it.payload.data.category,
                        origin = it.payload.data.origin,
                        destination = it.payload.data.destination
                    )

                }
                else -> {}
            }
        }
    }

    private fun setView(gtbir: GetTicketByIdResponse?) {
        gtbir?.data?.let {
            binding.apply {
                bundle.putString("AIRPLANE_NAME", it.airplaneName)
                bundle.putInt("PRICE_AIRPLANE", it.price!!)
                bundle.putInt("ID_TICKET_ORDER", it.id!!)
                airplaneName.text = it.airplaneName
                from.text = it.origin
                to.text = it.destination
                fromTime.text = it.departureTime?.substring(11, 16)
                toTime.text = it.arrivalTime?.substring(11, 16)
                category.text = it.category
                price.text = Utils.formatRupiah(it.price)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}