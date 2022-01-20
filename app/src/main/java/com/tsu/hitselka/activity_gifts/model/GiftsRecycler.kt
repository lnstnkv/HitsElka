package com.tsu.hitselka.activity_gifts.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ItemBrightGiftBinding
import com.tsu.hitselka.databinding.ItemFairytaleGiftBinding
import com.tsu.hitselka.databinding.ItemSpecialGiftBinding
import com.tsu.hitselka.model.Gift

class GiftsRecycler(
    private val context: Context,
    private val openClickListener: OpenClickListener
) :
    ListAdapter<Gift, RecyclerView.ViewHolder>(DIFF) {

    private companion object {
        const val BRIGHT = R.layout.item_bright_gift
        const val SPECIAL = R.layout.item_special_gift
        const val FAIRYTALE = R.layout.item_fairytale_gift

        val DIFF = object : DiffUtil.ItemCallback<Gift>() {
            override fun areItemsTheSame(oldItem: Gift, newItem: Gift) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Gift, newItem: Gift) = oldItem == newItem
        }
    }

    inner class BrightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBrightGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: Gift) = with(binding) {
            levelTextView.text = context.resources.getString(R.string.level, gift.level)
            countTextView.text = "x${gift.gifts}"
        }
    }

    inner class SpecialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSpecialGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: Gift) = with(binding) {
            levelTextView.text = context.resources.getString(R.string.level, gift.level)
            countTextView.text = "x${gift.gifts}"
        }
    }

    inner class FairytaleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFairytaleGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: Gift) = with(binding) {
            levelTextView.text = context.resources.getString(R.string.level, gift.level)
            countTextView.text = "x${gift.gifts}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            SPECIAL -> SpecialViewHolder(view)
            FAIRYTALE -> FairytaleViewHolder(view)
            else -> BrightViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BrightViewHolder -> holder.bind(getItem(position))
            is SpecialViewHolder -> holder.bind(getItem(position))
            is FairytaleViewHolder -> holder.bind(getItem(position))
            else -> throw Exception()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            "special" -> SPECIAL
            "fairytale" -> FAIRYTALE
            else -> BRIGHT
        }
    }

    interface OpenClickListener {
        fun onOpenClick(gift: Gift)
    }
}