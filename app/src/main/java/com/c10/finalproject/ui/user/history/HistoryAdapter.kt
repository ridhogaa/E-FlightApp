package com.c10.finalproject.ui.user.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.databinding.ItemListHistoryBinding
import com.c10.finalproject.utils.Utils

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class HistoryAdapter(private val listData: List<DataTicket>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: ItemListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataTicket) {
            with(binding) {
                textView.text = data.airplaneName
                textView8.text = data.origin
                textView9.text = data.destination
                textView10.text = Utils.formatRupiah(data.price!!)
                textView11.text = data.category
                textView6.text = data.departureTime?.substring(11, 16)
                textView7.text = data.arrivalTime?.substring(11, 16)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            ItemListHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listData[position])
    }
}