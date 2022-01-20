package com.tsu.hitselka.shop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ItemImprovementBinding
import com.tsu.hitselka.databinding.ItemShopBinding
import com.tsu.hitselka.model.ItemShop
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.record_book.ImprovementAdapter

class ShopAdapter (
    private val context: Context,
    private val listener: ShopAdapterListener
) : ListAdapter<ItemShop, ShopAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ItemShop>() {
            override fun areItemsTheSame(oldItem: ItemShop, newItem: ItemShop) = oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemShop, newItem: ItemShop) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemShopBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(shopItem: ItemShop) = with(binding) {

        }
    }

    interface ShopAdapterListener {
        fun onItemClick(item: ItemShop)
    }
}
