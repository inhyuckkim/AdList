package com.inhyuck.adlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.inhyuck.adlist.R
import com.inhyuck.adlist.databinding.ItemAdlistBinding
import com.inhyuck.adlist.db.entity.Ad
import com.inhyuck.adlist.ui.base.DataListAdapter

class AdAdapter (
    private val callback: ((Ad) -> Unit)?
) : DataListAdapter<Ad, ItemAdlistBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.appId == newItem.appId
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.numberOfDownloads == newItem.numberOfDownloads
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemAdlistBinding {
        val binding = DataBindingUtil.inflate<ItemAdlistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_adlist,
            parent,
            false
        )
        binding.root.setOnClickListener{
            binding.adItem?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemAdlistBinding, item: Ad) {
        binding.adItem = item
    }
}