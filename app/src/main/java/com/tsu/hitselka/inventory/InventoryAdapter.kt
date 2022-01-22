package com.tsu.hitselka.inventory


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.model.Inventory
import androidx.recyclerview.widget.AsyncListDiffer
import com.tsu.hitselka.databinding.ItemInventoryBinding


class InventoryAdapter: RecyclerView.Adapter<InventoryAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInventoryBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movieItem= differ.currentList[position]
        holder.bindView(movieItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object: DiffUtil.ItemCallback<Inventory>() {
        override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val list = differ.currentList.toMutableList()
        val fromItem = list[fromPosition]
        list.removeAt(fromPosition)
        if (toPosition < fromPosition) {
            list.add(toPosition + 1 , fromItem)
        } else {
            list.add(toPosition - 1, fromItem)
        }
        differ.submitList(list)
    }

    inner class ItemViewHolder(val binding: ItemInventoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Inventory) {
            binding.apply {
                imageViewInventory.setImageResource(item.image)
                textViewCount.text = item.count.toString()
            }
        }
    }
    interface InventoryAdapterListener {
        fun onItemClick(item: Inventory)
    }
}