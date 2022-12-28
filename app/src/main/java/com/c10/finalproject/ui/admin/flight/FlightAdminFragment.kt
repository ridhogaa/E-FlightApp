package com.c10.finalproject.ui.admin.flight

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentAdminFlightBinding
import androidx.fragment.app.activityViewModels
import com.c10.finalproject.wrapper.Resource
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightAdminFragment : Fragment() {

    private val viewModel: FlightAdminViewModel by activityViewModels()
    private var _binding: FragmentAdminFlightBinding? = null
    private val binding get() = _binding!!

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

    private fun saveTicket(){

        binding.btnSaveAdd.setOnClickListener {

            val originPlace = binding.etFrom.text.toString().trim()
            val directionPlace = binding.etTo.text.toString().trim()
            val departureDate = binding.etDepartureDateFlight.text.toString().trim()
            val returnDate = binding.etReturnDateFlight.text.toString().trim()
            val departTime1 = binding.etTimeDepartureFlight.text.toString().trim()
            val landingTime1 = binding.etTimeLandingFlight.text.toString().trim()
            val departTime2 = binding.etTimeDeparture2Flight.text.toString().trim()
            val landingTime2 = binding.etTimeLanding2Flight.text.toString().trim()
            val price = binding.etPriceFlight.text.toString().trim()
            val choosenCategory = chooseCategory

            // check whether origin and direction is the same place or not
            if (originPlace != directionPlace){
                // do post method


            } else {
                // set error
                binding.tilTo.setError("Origin and direction couldn't be same!")
                binding.tilTo.requestFocus()
            }
        }


    }

    private fun observeDirection() {
        viewModel.ticket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
                is Resource.Success -> {

                    setLoadingState(false)

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
    private fun departLandingTime(textInputEditText: TextInputEditText){
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()

        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                textInputEditText.setText(String.format("%d : %d", hourOfDay, minute))
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
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
                        etDepartureDateFlight.setText(date)
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
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
                        etReturnDateFlight.setText(date)

                        Log.d("Dateee", date)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbAddTicketAdmin.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}