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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.c10.finalproject.ui.user.search.SearchAdapter
import com.c10.finalproject.wrapper.Resource
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
        observeTicket()
        chooseCategory = "one_way"
        swapOnClick()
        departureDate()
        returnDate()
        chooseDeparture()
        observe()
        buttonSearchOnClick()
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

    private fun buttonSearchOnClick() {
        binding.btnSearch.setOnClickListener {
            val from = binding.etFrom.text.toString().trim()
            val to = binding.etTo.text.toString().trim()
            val departureDate = binding.etDepartureDate.text.toString().trim()
            val returnDate = binding.etReturnDate.text.toString().trim()
            val bundle = Bundle()
            if (from.isNotEmpty() && to.isNotEmpty() && departureDate.isNotEmpty()) {
                if (from != to) {
                    if (chooseCategory.equals("one_way", true)) {
                        bundle.putString("CATEGORY", chooseCategory)
                        bundle.putString("FROM", from)
                        bundle.putString("TO", to)
                        bundle.putString("DEPARTURE", departureDate)
                        bundle.putString("RETURN", null)
                        it.findNavController()
                            .navigate(R.id.action_homeFragment_to_searchResultFragment, bundle)
                    } else if (chooseCategory.equals("round_trip", true) && returnDate.isNotEmpty()) {
                        if (departureDate != returnDate){
                            bundle.putString("CATEGORY", chooseCategory)
                            bundle.putString("FROM", from)
                            bundle.putString("TO", to)
                            bundle.putString("DEPARTURE", departureDate)
                            bundle.putString("RETURN", returnDate)
                            it.findNavController()
                                .navigate(R.id.action_homeFragment_to_searchResultFragment, bundle)
                        } else {
                            Toast.makeText(requireContext(), "Departure Date and Return Date must not match", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Field must not empty", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "From and to must not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Field must not empty", Toast.LENGTH_SHORT).show()
            }
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
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
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
                        val date = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
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
            chooseCategory = "one_way"
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
            chooseCategory = "round_trip"
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