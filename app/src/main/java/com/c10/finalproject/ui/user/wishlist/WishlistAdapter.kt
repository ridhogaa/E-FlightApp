package com.c10.finalproject.ui.user.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.model.ticket.DataTicket
import com.c10.finalproject.databinding.ItemListWishlistBinding
import com.c10.finalproject.utils.Utils

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class WishlistAdapter(private val listData: List<DataTicket>) :
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(private val binding: ItemListWishlistBinding) :
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
            binding.cvWishlist.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("ID_TICKET", data.id!!)
                it.findNavController()
                    .navigate(R.id.action_wishlistFragment_to_flightDetailsFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder =
        WishlistViewHolder(
            ItemListWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(listData[position])
    }
}