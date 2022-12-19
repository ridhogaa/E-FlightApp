package com.c10.finalproject.ui.user.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.tickets.model.Data
import com.c10.finalproject.databinding.ItemListSearchBinding
import com.c10.finalproject.databinding.ItemListSearchBinding.inflate
import com.c10.finalproject.utils.Utils

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class SearchAdapter(private val listData: List<Data>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(private val binding: ItemListSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            with(binding) {
                textView.text = data.airplaneName
                textView8.text = data.origin
                textView9.text = data.destination
                textView10.text = Utils.formatRupiah(data.price!!)
                textView11.text = data.category
                textView6.text = data.departureTime?.substring(11, 16)
                textView7.text = data.arrivalTime?.substring(11, 16)
                textView2.text = data.departureTime?.substring(0, 10)
            }
            binding.btnSrCheck.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("ID_TICKET", data.id!!)
                it.findNavController()
                    .navigate(R.id.action_searchResultFragment_to_flightDetailsFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(listData[position])
    }
}