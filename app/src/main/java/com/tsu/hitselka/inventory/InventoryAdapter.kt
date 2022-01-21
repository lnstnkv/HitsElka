package com.tsu.hitselka.inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ItemShopBinding
import com.tsu.hitselka.model.Inventory
import com.tsu.hitselka.model.ItemShop
import com.tsu.hitselka.shop.ShopAdapter

class InventoryAdapter (
    private val context: Context,
    private val listener: InventoryAdapterListener
) : ListAdapter<Inventory, InventoryAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Inventory>() {
            override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemShopBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(inventory: Inventory) = with(binding) {

        }
    }

    interface InventoryAdapterListener {
        fun onItemClick(item: Inventory)
    }
}
