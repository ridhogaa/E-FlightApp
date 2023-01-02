package com.c10.finalproject.ui.user.home

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentHomeBinding
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getToken().observe(viewLifecycleOwner) {
            viewModel.getUserByToken(it)
        }
        chooseCategory = "one_way"
        departureDate()
        returnDate()
        chooseDeparture()
        observe()
        buttonSearchOnClick()
    }

    private fun isImageEmpty(urlImage: String) {
        if (urlImage.isNotEmpty()) {
            Glide.with(requireContext()).load(urlImage).into(binding.imageProfile)
        } else {
            binding.imageProfile.background =
                resources.getDrawable(R.drawable.ic_photo_profile)
        }
    }

    private fun observe() {
        viewModel.getTickets()
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbHome.isVisible = true
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbHome.isVisible = false
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbHome.isVisible = false
                    binding.stateError.isVisible = false
                    binding.constraintLayout.isVisible = true
                    binding.apply {
                        tvHello.text = "Hello, ${it.data.data?.name}"
                        it.data.data?.photoProfile?.let { it1 -> isImageEmpty(it1) }
                    }
                }
                else -> {

                }
            }
        }
        viewModel.ticket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbHome.isVisible = true
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = false
                }
                is Resource.Error -> {
                    binding.pbHome.isVisible = false
                    binding.constraintLayout.isVisible = false
                    binding.stateError.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbHome.isVisible = false
                    binding.stateError.isVisible = false
                    binding.constraintLayout.isVisible = true
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_textview, it.data)
                    swapOnClick(adapter)
                    (binding.etFrom as? AutoCompleteTextView)?.setAdapter(adapter)
                    (binding.etTo as? AutoCompleteTextView)?.setAdapter(adapter)
                }
                else -> {

                }
            }
        }
    }

    private fun swapOnClick(adapter: ArrayAdapter<String>) {
        binding.swapFromTo.setOnClickListener {
            val from = binding.etFrom.text.toString().trim()
            val to = binding.etTo.text.toString().trim()
            binding.etTo.setText(from)
            binding.etFrom.setText(to)
            binding.swapToFrom.isVisible = true
            binding.swapFromTo.isVisible = false
            (binding.etFrom as? AutoCompleteTextView)?.setAdapter(adapter)
            (binding.etTo as? AutoCompleteTextView)?.setAdapter(adapter)
        }
        binding.swapToFrom.setOnClickListener {
            val from = binding.etFrom.text.toString().trim()
            val to = binding.etTo.text.toString().trim()
            binding.etTo.setText(to)
            binding.etFrom.setText(from)
            binding.swapToFrom.isVisible = false
            binding.swapFromTo.isVisible = true
            (binding.etFrom as? AutoCompleteTextView)?.setAdapter(adapter)
            (binding.etTo as? AutoCompleteTextView)?.setAdapter(adapter)
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
                    } else if (chooseCategory.equals(
                            "round_trip",
                            true
                        ) && returnDate.isNotEmpty()
                    ) {
                        if (departureDate != returnDate) {
                            bundle.putString("CATEGORY", chooseCategory)
                            bundle.putString("FROM", from)
                            bundle.putString("TO", to)
                            bundle.putString("DEPARTURE", departureDate)
                            bundle.putString("RETURN", returnDate)
                            it.findNavController()
                                .navigate(R.id.action_homeFragment_to_searchResultFragment, bundle)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Departure Date and Return Date must not match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Field must not empty", Toast.LENGTH_SHORT)
                            .show()
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
                        if (dayOfMonth in 1..9 && monthOfYear in 0..9) {
                            etDepartureDate.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etDepartureDate.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 0..9) {
                            etDepartureDate.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etDepartureDate.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
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
            etReturnDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        if (dayOfMonth in 1..9 && monthOfYear in 0..9) {
                            etReturnDate.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etReturnDate.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 0..9) {
                            etReturnDate.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etReturnDate.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
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