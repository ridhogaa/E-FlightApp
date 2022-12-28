package com.c10.finalproject.ui.admin.home.ticket.edit

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.tickets.model.GetTicketByIdResponse
import com.c10.finalproject.databinding.FragmentEditTicketBinding
import com.c10.finalproject.utils.Utils
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditTicketFragment : Fragment() {

    private val viewModel: EditTicketViewModel by activityViewModels()
    private var _binding: FragmentEditTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetailID()

        // get data
        departureDate()
        returnDate()

        // observe
        observeData()
        observeTicket()
        // button triggered
//        updateListener()
    }

    private fun updateListener() {
        binding.btnSaveEdit.setOnClickListener {

            val category = binding.etTypeFlight.text.toString().trim()

            if (category.equals("one_way")) {
                val dateFrom1 = binding.etFrom.text.toString().trim()
                val dateTo1 = binding.etTo.text.toString().trim()

            } else {
//            val dateFrom2 = binding.etFrom.text.toString().trim()
//            val dateTo2 = binding.etTo.text.toString().trim()
                // timeFrom2
                // timeFrom2

            }


            val departureDate = binding.etDepartureDateFlight.text.toString().trim()
            val returnDate = binding.etReturnDateFlight.text.toString().trim()

        }

    }

    private fun getDetailID() {
        viewModel.getDetail(arguments?.getInt("ID_TICKET")!!)
    }

    private fun observeTicket() {
        viewModel.ticket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
                is Resource.Success -> {
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_textview, it.data)
                    (binding.etFrom as? AutoCompleteTextView)?.setAdapter(adapter)
                    (binding.etTo as? AutoCompleteTextView)?.setAdapter(adapter)

                }
                else -> {
                }
            }
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun departureDate() {
        binding.apply {
            etDepartureDateFlight.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), { view, year, monthOfYear, dayOfMonth ->
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
                        etDepartureDateFlight.setText(date)
                    }, year, month, day
                )
                datePickerDialog.show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun returnDate() {
        binding.apply {
            etReturnDateFlight.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), { view, year, monthOfYear, dayOfMonth ->
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
                        etReturnDateFlight.setText(date)
                    }, year, month, day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun setView(gtbir: GetTicketByIdResponse?) {
        gtbir?.data?.let {
            binding.apply {
                etNameFlight.setText(it.airplaneName)
                etTypeFlight.setText(it.category)
                etFrom.setText(it.origin)
                etTo.setText(it.destination)
                etTimeDepartureFlight.setText(it.departureTime?.substring(11, 16))
                etTimeLandingFlight.setText(it.arrivalTime?.substring(11, 16))
                etTypeFlight.setText(it.category)
                etPriceFlight.setText(Utils.formatRupiah(it.price!!))
                etDepartureDateFlight.setText(it.departureTime?.substring(0, 10))
                if (it.category.equals("one_way", true)) {
                    tilReturnDateFlight.visibility = View.GONE
                    llReturnFlight.visibility = View.GONE
                } else {
                    tilReturnDateFlight.visibility = View.VISIBLE
                    llReturnFlight.visibility = View.VISIBLE

                    // date
                    etReturnDateFlight.setText(it.returnTime?.toString()?.substring(0, 10))

                    // time 2
                    etTimeDeparture2Flight.setText(it.returnTime?.toString()?.substring(11, 16))
                    etTimeLanding2Flight.setText(it.arrival2Time?.toString()?.substring(11, 16))

                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbEditTicketAdmin.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}