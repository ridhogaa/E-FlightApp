package com.c10.finalproject.ui.user.transaction

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentBottomSheetTransactionBinding
import com.c10.finalproject.databinding.FragmentLoginBinding
import com.c10.finalproject.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class BottomSheetTransactionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomSheetTransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        btnCancel()
        btnBooking()
    }

    private fun btnBooking() {
        binding.btnConfirmBts.setOnClickListener {
            if (binding.etBalance.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Field must not empty", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.etBalance.text.toString()
                        .toInt() >= arguments?.getInt("PRICE_AIRPLANE")!!
                ) {
                    viewModel.getToken().observe(viewLifecycleOwner) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Message Dialog")
                        builder.setMessage(
                            "Are you sure you want to buy this ticket? return your balance ${
                                Utils.formatRupiah(
                                    binding.etBalance.text.toString()
                                        .toInt() - arguments?.getInt("PRICE_AIRPLANE")!!
                                )
                            }, you will be directed to the home to check your order history!"
                        )

                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                            viewModel.addOrder(
                                it,
                                arguments?.getInt("ID_TICKET_ORDER")!!
                            )
                            findNavController().navigate(R.id.homeFragment)
                        }

                        builder.setNegativeButton(android.R.string.no) { dialog, which ->

                        }
                        builder.show()

                    }
                } else {
                    Toast.makeText(requireContext(), "Low Balance!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun btnCancel() {
        binding.btnCancelBts.setOnClickListener {
            dismiss()
        }
    }

    private fun setView() {
        binding.apply {
            airplaneName.text = arguments?.getString("AIRPLANE_NAME")
            price.text = "Price: " + Utils.formatRupiah(arguments?.getInt("PRICE_AIRPLANE")!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}