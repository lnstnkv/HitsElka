package com.tsu.hitselka.activity_gifts.model

import android.content.Context
import android.util.Log
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
import com.tsu.hitselka.model.GiftInfo

class GiftsRecycler(
    private val context: Context,
    private val openClickListener: OpenClickListener
) :
    ListAdapter<GiftInfo, RecyclerView.ViewHolder>(DIFF) {

    private companion object {
        const val BRIGHT = R.layout.item_bright_gift
        const val SPECIAL = R.layout.item_special_gift
        const val FAIRYTALE = R.layout.item_fairytale_gift

        val DIFF = object : DiffUtil.ItemCallback<GiftInfo>() {
            override fun areItemsTheSame(oldItem: GiftInfo, newItem: GiftInfo) = oldItem == newItem
            override fun areContentsTheSame(oldItem: GiftInfo, newItem: GiftInfo) = oldItem == newItem
        }
    }

    inner class BrightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBrightGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: GiftInfo) = with(binding) {
            if (gift.gift.gifts == 0) {
                openButton.isClickable = false
            }

            firstAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minFirstReward, gift.maxFirstReward)
            secondAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minSecondReward, gift.maxSecondReward)
            thirdAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minThirdReward, gift.maxThirdReward)

            levelTextView.text = context.resources.getString(R.string.level, gift.gift.level)
            countTextView.text = context.resources.getString(R.string.giftsCount, gift.gift.gifts)

            val progress = gift.gift.giftsOpened
            val width = getWidth(progress)
            if (width > 0) {
                progressView.visibility = View.VISIBLE
                progressView.layoutParams.width = width
            }
        }
    }

    inner class SpecialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSpecialGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: GiftInfo) = with(binding) {
            if (gift.gift.gifts == 0) {
                openButton.isClickable = false
            }

            firstAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minFirstReward, gift.maxFirstReward)
            secondAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minSecondReward, gift.maxSecondReward)
            thirdAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minThirdReward, gift.maxThirdReward)

            levelTextView.text = context.resources.getString(R.string.level, gift.gift.level)
            countTextView.text = context.resources.getString(R.string.giftsCount, gift.gift.gifts)

            val progress = gift.gift.giftsOpened * 10
            val width = getWidth(progress)
            if (width > 0) {
                progressView.visibility = View.VISIBLE
                progressView.layoutParams.width = width
            }
        }
    }

    inner class FairytaleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFairytaleGiftBinding.bind(view)

        init {
            binding.openButton.setOnClickListener {
                openClickListener.onOpenClick(getItem(layoutPosition))
            }
        }

        fun bind(gift: GiftInfo) = with(binding) {
            if (gift.gift.gifts == 0) {
                openButton.isClickable = false
            }

            firstAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minFirstReward, gift.maxFirstReward)
            secondAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minSecondReward, gift.maxSecondReward)
            thirdAwardTextView.text = context.resources.getString(R.string.giftsReward, gift.minThirdReward, gift.maxThirdReward)

            levelTextView.text = context.resources.getString(R.string.level, gift.gift.level)
            countTextView.text = context.resources.getString(R.string.giftsCount, gift.gift.gifts)

            val progress = gift.gift.giftsOpened * 50
            val width = getWidth(progress)
            if (width > 0) {
                progressView.visibility = View.VISIBLE
                progressView.layoutParams.width = width
            }
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
        return when (getItem(position).gift.type) {
            "special" -> SPECIAL
            "fairytale" -> FAIRYTALE
            else -> BRIGHT
        }
    }

    interface OpenClickListener {
        fun onOpenClick(gift: GiftInfo)
    }

    private fun getWidth(percentage: Int): Int {
        val maxWidth = context.resources.getDimensionPixelSize(R.dimen.gift_progress_width)
        return maxWidth * percentage / 100
    }
}