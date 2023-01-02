package com.c10.finalproject.ui.admin.flight

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
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
import com.c10.finalproject.data.remote.tickets.model.ticket.add.AddTicketBody
import com.c10.finalproject.data.remote.tickets.model.ticket.update.UpdateTicketBody
import com.c10.finalproject.databinding.FragmentAdminFlightBinding
import com.c10.finalproject.wrapper.Resource
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlightAdminFragment : Fragment() {

    private val viewModel: FlightAdminViewModel by activityViewModels()
    private var _binding: FragmentAdminFlightBinding? = null
    private val binding get() = _binding!!

    private var job: Job = Job()

    private lateinit var chooseCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminFlightBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // category
        chooseCategory = "one_way"
        chooseDeparture()

        // direction
        observeDirection()

        // date
        departureDate()
        returnDate()

        // time
        departLandingTime(binding.etTimeDepartureFlight)
        departLandingTime(binding.etTimeLandingFlight)
        departLandingTime(binding.etTimeDeparture2Flight)
        departLandingTime(binding.etTimeLanding2Flight)

        // save ticket
        saveTicket()
    }

    private fun saveTicket() {

        binding.btnSaveAdd.setOnClickListener {

            if (validationInput()) {

                val airplaneName = binding.etAirplaneName.text.toString().trim()

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
                val choosenCategory = chooseCategory

                // check whether origin and direction is the same place or not
                if (originPlace != directionPlace) {
                    // do post method

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Message Dialog")
                    builder.setMessage(
                        "Are you sure to add this ticket?"
                    )

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        viewModel.getToken().observe(viewLifecycleOwner) {

                            // do post method

                            if (choosenCategory.equals("one_way")) {

                                lifecycleScope.launchWhenResumed {
                                    if (job.isActive) job.cancel()
                                    job = launch {
                                        viewModel.addTicket(
                                            it, AddTicketBody(
                                                airplaneName = airplaneName,
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
                                                    "Success to Add Ticket",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                findNavController().navigate(R.id.action_flightFragmentAdmin_to_homeFragmentAdmin)
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
                                        viewModel.addTicket(
                                            it, AddTicketBody(
                                                airplaneName = airplaneName,
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
                                                    "Success to Add Ticket",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                findNavController().navigate(R.id.action_flightFragmentAdmin_to_homeFragmentAdmin)
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

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->

                    }
                    builder.show()

                } else {
                    // set error
                    binding.tilTo.setError("Origin and direction couldn't be same!")
                    binding.tilTo.requestFocus()
                }
            }

        }


    }

    private fun observeDirection() {

        viewModel.getTickets()

        viewModel.ticket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                    binding.constraintLayoutFlightAdmin.isVisible = false
                    binding.stateError.isVisible = false
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    binding.constraintLayoutFlightAdmin.isVisible = false
                    binding.stateError.isVisible = true
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    binding.stateError.isVisible = false
                    binding.constraintLayoutFlightAdmin.isVisible = true

                    val adapter = ArrayAdapter(requireContext(), R.layout.list_textview, it.data)
                    (binding.etFrom as? AutoCompleteTextView)?.setAdapter(adapter)
                    (binding.etTo as? AutoCompleteTextView)?.setAdapter(adapter)
                }
                else -> {
                }
            }
        }
    }

    private fun chooseDeparture() {
        binding.btnOneWay.setOnClickListener {
            chooseCategory = "one_way"
            binding.tilReturnDateFlight.visibility = View.GONE
            binding.llReturnFlight.visibility = View.GONE

            // set button one way
            binding.btnOneWay.setTextColor(resources.getColor(R.color.white))
            binding.btnOneWay.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.btnOneWay.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.blue))

            //  set button roundtrip
            binding.btnRoundTrip.setTextColor(resources.getColor(R.color.blue))
            binding.btnRoundTrip.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.btnRoundTrip.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.black))
        }

        binding.btnRoundTrip.setOnClickListener {
            chooseCategory = "round_trip"
            binding.tilReturnDateFlight.visibility = View.VISIBLE
            binding.llReturnFlight.visibility = View.VISIBLE

            // set button round trip
            binding.btnRoundTrip.setTextColor(resources.getColor(R.color.white))
            binding.btnRoundTrip.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.btnRoundTrip.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.blue))

            // set button one way
            binding.btnOneWay.setTextColor(resources.getColor(R.color.blue))
            binding.btnOneWay.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.btnOneWay.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.black))
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
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        if (dayOfMonth in 1..9 && monthOfYear in 0..9) {
                            etDepartureDateFlight.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etDepartureDateFlight.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 0..9) {
                            etDepartureDateFlight.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etDepartureDateFlight.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
                        }
                    },
                    year,
                    month,
                    day
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
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        if (dayOfMonth in 1..9 && monthOfYear in 0..9) {
                            etReturnDateFlight.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etReturnDateFlight.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 0..9) {
                            etReturnDateFlight.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etReturnDateFlight.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
                        }
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun validationInput(): Boolean {

        var isValid = true

        val airplaneName = binding.etAirplaneName.toString().trim()

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


        if (airplaneName.isEmpty()) {
            isValid = false
            binding.tilAirplaneName.error = "This field must not be empty!"
            binding.tilAirplaneName.requestFocus()
        }

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

        if (chooseCategory.equals("round_trip")) {
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
        binding.pbAddTicketAdmin.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}