package com.c10.finalproject.ui.user.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.data.remote.model.notification.DataNotification
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.databinding.ItemListNotificationBinding
import com.c10.finalproject.databinding.ItemListNotificationBinding.inflate

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class NotificationAdapter(
    private val listData: List<DataNotification>,
    private val listDataTicket: List<DataTicket>
) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(private val binding: ItemListNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataNotification, dataTicket: DataTicket) {
            with(binding) {
                tvSummary.text = "Summary of your ticket, order id: ${data.orderId}"
                tvNotification.text =
                    "${dataTicket.airplaneName} | ${dataTicket.origin}-${dataTicket.destination}."
                tvThanks.text = "Has been successfully ordered."
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder(
            inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(listData[position], listDataTicket[position])
    }
}