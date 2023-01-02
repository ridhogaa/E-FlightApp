package com.c10.finalproject.ui.admin.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.model.ticket.Data
import com.c10.finalproject.databinding.ItemFligtHomeBinding
import com.c10.finalproject.utils.Utils

class HomeAdminAdapter: RecyclerView.Adapter<HomeAdminAdapter.HomeAdminViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.airplaneName == newItem.airplaneName
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(ticket: List<Data>?) {
        differ.submitList(ticket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdminViewHolder {
        val binding = ItemFligtHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdminViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class HomeAdminViewHolder(private val binding: ItemFligtHomeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            with(binding) {
                tvCodeItemHome.text = data.airplaneName
                tvFromItemHome.text = data.origin
                tvToItemHome.text = data.destination
                tvPriceItemHome.text = Utils.formatRupiah(data.price!!)
                tvTypeItemHome.text = data.category
                tvDepartureTimeItemHome.text = data.departureTime?.substring(11, 16)
                tvLandingTimeItemHome.text = data.arrivalTime?.substring(11, 16)

                cvTicketHomeAdmin.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("ID_TICKET", data.id!!)
                    it.findNavController()
                        .navigate(R.id.action_homeFragmentAdmin_to_editTicketAdminFragment, bundle)
                }
            }
        }
    }

}