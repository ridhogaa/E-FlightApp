package com.c10.finalproject.ui.admin.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c10.finalproject.data.remote.tickets.model.histories.DataUsers
import com.c10.finalproject.databinding.ItemListTransactionBinding


class ShowUserAdapter: RecyclerView.Adapter<ShowUserAdapter.ShowUserViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<DataUsers>() {
        override fun areItemsTheSame(oldItem: DataUsers, newItem: DataUsers): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataUsers, newItem: DataUsers): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(user: List<DataUsers>?) {
        differ.submitList(user)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowUserViewHolder {
        val binding = ItemListTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowUserViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class ShowUserViewHolder(private val binding: ItemListTransactionBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataUsers) {
            with(binding) {
                tvUsernameItemTrans.text = data.username

            }
        }
    }

}