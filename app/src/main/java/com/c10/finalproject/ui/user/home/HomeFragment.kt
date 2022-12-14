package com.c10.finalproject.ui.user.home

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var chooseCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseCategory = "One-Way"
        swapOnClick()
        departureDate()
        returnDate()
        chooseDeparture()
        observe()
    }

    private fun swapOnClick() {
        binding.swapFromTo.setOnClickListener {
            val from = binding.etFrom.text.toString().trim()
            val to = binding.etTo.text.toString().trim()
            binding.etTo.setText(from)
            binding.etFrom.setText(to)
            binding.swapToFrom.visibility = View.VISIBLE
            binding.swapFromTo.visibility = View.GONE

        }
        binding.swapToFrom.setOnClickListener {
            val from = binding.etFrom.text.toString().trim()
            val to = binding.etTo.text.toString().trim()
            binding.etTo.setText(to)
            binding.etFrom.setText(from)
            binding.swapFromTo.visibility = View.VISIBLE
            binding.swapToFrom.visibility = View.GONE
        }
    }

    private fun observe() {
        viewModel.user.observe(viewLifecycleOwner) {
            it.onSuccess { response ->
                binding.apply {
                    tvHello.text = "Hello, ${response.data?.name}"
                    isImageEmpty(response.data?.photoProfile.toString())
                }
            }
            it.onFailure {

            }
        }
    }

    private fun isImageEmpty(urlImage: String) {
        if (urlImage.isNotEmpty()) {
            Glide.with(requireContext()).load(urlImage).into(binding.imageProfile)
        } else {
            binding.imageProfile.background =
                resources.getDrawable(R.drawable.ic_baseline_account_circle_24)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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

    @RequiresApi(Build.VERSION_CODES.N)
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
            chooseCategory = "One-Way"
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
            chooseCategory = "Round-Trip"
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