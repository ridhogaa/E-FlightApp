package com.c10.finalproject.ui.admin.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.databinding.ItemListTransactionBinding
import com.c10.finalproject.utils.Utils


class TransactionAdminAdapter: RecyclerView.Adapter<TransactionAdminAdapter.TransactionAdminViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(ticket: List<Data>?) {
        differ.submitList(ticket)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdminViewHolder {
        val binding = ItemListTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionAdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionAdminViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class TransactionAdminViewHolder(private val binding: ItemListTransactionBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            with(binding) {
                tvCodeItemTrans.text = data.airplaneName
                tvFromItemTrans.text = data.origin
                tvToItemTrans.text = data.destination
                tvTotalItemTrans.text = Utils.formatRupiah(data.price!!)
                tvTypeItemTrans.text = data.category
                tvDepartureTimeItemTrans.text = data.departureTime?.substring(11, 16)
                tvLandingTimeItemTrans.text = data.arrivalTime?.substring(11, 16)
            }
        }
    }

}