package com.tsu.hitselka.activity_record_book

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ItemImprovementBinding
import com.tsu.hitselka.model.Object

class ImprovementAdapter(
    private val context: Context,
    private val listener: ImprovementAdapterListener
) : ListAdapter<Object, ImprovementAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Object>() {
            override fun areItemsTheSame(oldItem: Object, newItem: Object) = oldItem.type == newItem.type && oldItem.level == newItem.level
            override fun areContentsTheSame(oldItem: Object, newItem: Object) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_improvement, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImprovementBinding.bind(view)

        init {
            binding.button.setOnClickListener {
                listener.onItemClick(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(item: Object) = with(binding) {
            objectImageView.setImageResource(item.image)
            stageTextView.text = context.resources.getString(R.string.stage_list, item.level)
            titleTextView.setText(item.name)

            if (item.built || item.locked) {
                progressView.visibility = View.INVISIBLE
                progressBgView.visibility = View.INVISIBLE
                progressTextView.visibility = View.INVISIBLE
                button.visibility = View.INVISIBLE
                doneImageView.visibility = View.VISIBLE
                doneTextView.visibility = View.VISIBLE

                if (item.locked) {
                    doneImageView.setImageResource(R.drawable.ic_locked)
                    doneTextView.text = context.resources.getString(R.string.locked, item.level - 1)
                }

                return@with
            }

            val progress = getPercentage(item)
            progressTextView.text = "$progress%"

            val width = getWidth(progress)
            if (width == 0) {
                progressView.visibility = View.INVISIBLE
            } else {
                progressView.layoutParams.width = width
            }
        }
    }

    interface ImprovementAdapterListener {
        fun onItemClick(item: Object)
    }

    private fun getPercentage(item: Object) = item.wandsSpent * 100 / item.wandsNeeded

    private fun getWidth(percentage: Long): Int {
        val maxWidth = context.resources.getDimensionPixelSize(R.dimen.object_progress_width)
        return maxWidth * percentage.toInt() / 100
    }
}
