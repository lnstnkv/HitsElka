package com.tsu.hitselka.activity_gift.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ItemGiftAwardBinding
import com.tsu.hitselka.model.Inventory

class RewardAdapter() : ListAdapter<Inventory, RewardAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Inventory>() {
            override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_gift_award, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGiftAwardBinding.bind(view)

        fun bind(item: Inventory) = with(binding) {
            rewardImageView.setImageResource(item.image)
            rewardTextView.text = item.count.toString()
        }
    }
}
