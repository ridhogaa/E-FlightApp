package com.c10.finalproject.ui.user.home

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        departureDate()
        returnDate()
        chooseDeparture()
    }

    private fun departureDate() {
        binding.apply {
            etDepartureDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        val date = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                        etDepartureDate.setText(date)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun returnDate() {
        binding.apply {
            etReturnDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        val date = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                        etReturnDate.setText(date)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun chooseDeparture() {
        binding.btnOneWay.setOnClickListener {
            binding.tilReturnDate.visibility = View.GONE
            binding.btnOneWay.setTextColor(resources.getColor(R.color.white))
            binding.btnOneWay.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.btnRoundTrip.setTextColor(resources.getColor(R.color.blue))
            binding.btnRoundTrip.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.btnRoundTrip.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.black))
            binding.btnOneWay.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.blue))
        }

        binding.btnRoundTrip.setOnClickListener {
            binding.tilReturnDate.visibility = View.VISIBLE
            binding.btnRoundTrip.setTextColor(resources.getColor(R.color.white))
            binding.btnRoundTrip.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.btnOneWay.setTextColor(resources.getColor(R.color.blue))
            binding.btnOneWay.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.btnRoundTrip.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.btnOneWay.strokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.black))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}