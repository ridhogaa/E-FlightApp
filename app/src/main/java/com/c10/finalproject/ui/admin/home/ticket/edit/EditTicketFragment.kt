package com.c10.finalproject.ui.admin.home.ticket.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.model.ticket.GetTicketByIdResponse
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketBody
import com.c10.finalproject.databinding.FragmentEditTicketBinding
import com.c10.finalproject.wrapper.Resource
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EditTicketFragment : Fragment() {

    private val viewModel: EditTicketViewModel by activityViewModels()
    private var _binding: FragmentEditTicketBinding? = null
    private val binding get() = _binding!!

    private var job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe detail
        getDetailID()

        // observe
        observeDataSetView()
        observeTickets()

        // date
        departureDate()
        returnDate()

        // time
        departLandingTime(binding.etTimeDepartureFlight)
        departLandingTime(binding.etTimeLandingFlight)
        departLandingTime(binding.etTimeDeparture2Flight)
        departLandingTime(binding.etTimeLanding2Flight)

        // button listener
        buttonListerner()

    }


    private fun buttonListerner() {

        binding.btnSaveEdit.setOnClickListener {

            if (validationInput()) {

                val originPlace = binding.etFrom.text.toString().trim()
                val directionPlace = binding.etTo.text.toString().trim()

                // date
                val departureDate = binding.etDepartureDateFlight.text.toString().trim()
                val departTime1 = binding.etTimeDepartureFlight.text.toString().trim()
                val landingTime1 = binding.etTimeLandingFlight.text.toString().trim()

                // date used
                val departure_time = "${departureDate}T${departTime1}:00.000Z"
                val arrival_time = "${departureDate}T${landingTime1}:00.000Z"

                val returnDate = binding.etReturnDateFlight.text.toString().trim()
                val departTime2 = binding.etTimeDeparture2Flight.text.toString().trim()
                val landingTime2 = binding.etTimeLanding2Flight.text.toString().trim()

                //  date used
                val return_time = "${returnDate}T${departTime2}:00.000Z"
                val arrival2_time = "${returnDate}T${landingTime2}:00.000Z"

                Log.d("DATEEE", departure_time)

                val price = binding.etPriceFlight.text.toString().trim().toInt()
                val choosenCategory = binding.etTypeFlight.text.toString().trim()

                // check whether origin and direction is the same place or not
                if (originPlace != directionPlace) {
                    // do post method

                    viewModel.getToken().observe(viewLifecycleOwner) {

                        // do post method

                        if (choosenCategory.equals("one_way")) {

                            lifecycleScope.launchWhenResumed {
                                if (job.isActive) job.cancel()
                                job = launch {
                                    viewModel.updateTicket(
                                        it,
                                        arguments?.getInt("ID_TICKET")!!,
                                        UpdateTicketBody(
                                            departureTime = "${departure_time}",
                                            arrivalTime = "${arrival_time}",
                                            returnTime = null,
                                            arrival2Time = null,
                                            price = price,
                                            category = choosenCategory,
                                            origin = originPlace,
                                            destination = directionPlace,

                                            )
                                    ).collect {

                                        it.onSuccess { response ->
                                            Toast.makeText(
                                                requireContext(),
                                                "Success to Update Ticket",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            findNavController().navigate(R.id.action_editTicketAdminFragment_to_homeFragmentAdmin)
                                        }
                                        it.onFailure { responseFailure ->
                                            Toast.makeText(
                                                requireContext(),
                                                responseFailure.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                                }
                            }


                        } else {
                            lifecycleScope.launchWhenResumed {
                                if (job.isActive) job.cancel()
                                job = launch {
                                    viewModel.updateTicket(
                                        "Bearer $it",
                                        arguments?.getInt("ID_TICKET")!!,
                                        UpdateTicketBody(
                                            departureTime = "${departure_time}",
                                            arrivalTime = "${arrival_time}",
                                            returnTime = "${return_time}",
                                            arrival2Time = "${arrival2_time}",
                                            price = price,
                                            category = choosenCategory,
                                            origin = originPlace,
                                            destination = directionPlace,

                                            )
                                    ).collect {

                                        it.onSuccess { response ->
                                            Toast.makeText(
                                                requireContext(),
                                                "Success to Update Ticket",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            findNavController().navigate(R.id.action_editTicketAdminFragment_to_homeFragmentAdmin)
                                        }
                                        it.onFailure { responseFailure ->
                                            Toast.makeText(
                                                requireContext(),
                                                responseFailure.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                                }
                            }

                        }


                    }

                } else {
                    // set error
                    binding.tilTo.setError("Origin and direction couldn't be same!")
                    binding.tilTo.requestFocus()
                }
            }

        }

        binding.btnDeleteEdit.setOnClickListener {
            viewModel.getToken().observe(viewLifecycleOwner) {

                // do networking method
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        viewModel.deleteTicket(
                            it,
                            arguments?.getInt("ID_TICKET")!!,
                        ).collect {

                            it.onSuccess { response ->
                                Toast.makeText(
                                    requireContext(),
                                    "Success to Delete Ticket!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_editTicketAdminFragment_to_homeFragmentAdmin)
                            }
                            it.onFailure { responseFailure ->
                                Toast.makeText(
                                    requireContext(),
                                    responseFailure.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }
            }
        }
    }


    private fun getDetailID() {
        viewModel.getDetail(arguments?.getInt("ID_TICKET")!!)
    }

    private fun observeTickets() {
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun departLandingTime(textInputEditText: TextInputEditText) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()

        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    textInputEditText.setText(String.format("%02d:%02d", hourOfDay, minute))
                }
            }, hour, minute, false)

        textInputEditText.setOnClickListener { v ->
            mTimePicker.show()
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
                        if (dayOfMonth in 1..9 && monthOfYear in 1..9) {
                            etDepartureDateFlight.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etDepartureDateFlight.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 1..9) {
                            etDepartureDateFlight.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etDepartureDateFlight.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
                        }
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
                        if (dayOfMonth in 1..9 && monthOfYear in 1..9) {
                            etReturnDateFlight.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etReturnDateFlight.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 1..9) {
                            etReturnDateFlight.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etReturnDateFlight.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
                        }
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
                etPriceFlight.setText(it.price!!.toString().trim())
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

    private fun validationInput(): Boolean {

        var isValid = true
        val originPlace = binding.etFrom.text.toString().trim()
        val directionPlace = binding.etTo.text.toString().trim()

        // date
        val departureDate = binding.etDepartureDateFlight.text.toString().trim()
        val departTime1 = binding.etTimeDepartureFlight.text.toString().trim()
        val landingTime1 = binding.etTimeLandingFlight.text.toString().trim()

        val returnDate = binding.etReturnDateFlight.text.toString().trim()
        val departTime2 = binding.etTimeDeparture2Flight.text.toString().trim()
        val landingTime2 = binding.etTimeLanding2Flight.text.toString().trim()

        val price = binding.etPriceFlight.text.toString().trim()

        if (originPlace.isEmpty()) {
            isValid = false
            binding.tilFrom.error = "This field must not be empty!"
            binding.tilFrom.requestFocus()
        }

        if (directionPlace.isEmpty()) {
            isValid = false
            binding.tilTo.error = "This field must not be empty!"
            binding.tilTo.requestFocus()
        }

        if (departureDate.isEmpty()) {
            isValid = false
            binding.tilDepartureDateFlight.error = "This field must not be empty!"
            binding.tilDepartureDateFlight.requestFocus()
        }

        if (departTime1.isEmpty()) {
            isValid = false
            binding.tilTimeDepartureFlight.error = "This field must not be empty!"
            binding.tilTimeDepartureFlight.requestFocus()
        }

        if (landingTime1.isEmpty()) {
            isValid = false
            binding.tilTimeLandingFlight.error = "This field must not be empty!"
            binding.tilTimeLandingFlight.requestFocus()
        }

        if (binding.etTypeFlight.equals("round_trip")) {
            if (returnDate.isEmpty()) {
                isValid = false
                binding.tilReturnDateFlight.error = "This field must not be empty!"
                binding.tilReturnDateFlight.requestFocus()
            }

            if (departTime2.isEmpty()) {
                isValid = false
                binding.tilTimeDeparture2Flight.error = "This field must not be empty!"
                binding.tilTimeDeparture2Flight.requestFocus()
            }

            if (landingTime2.isEmpty()) {
                isValid = false
                binding.tilTimeLanding2Flight.error = "This field must not be empty!"
                binding.tilTimeLanding2Flight.requestFocus()
            }
        }


        if (price.isEmpty()) {
            isValid = false
            binding.tilPriceFlight.error = "This field must not be empty!"
            binding.tilPriceFlight.requestFocus()
        }

        return isValid
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbEditTicketAdmin.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}