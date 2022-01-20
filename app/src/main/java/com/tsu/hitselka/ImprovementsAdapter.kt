package com.tsu.hitselka

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.databinding.ItemImprovementBinding
import com.tsu.hitselka.model.Object

class ImprovementAdapter (
    private val listener: ChatAdapterListener
) : ListAdapter<Object, ImprovementAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Object>() {
            override fun areItemsTheSame(oldItem: Object, newItem: Object) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Object, newItem: Object) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_improvement, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImprovementBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(profileItem: Object) = with(binding) {
        }
    }
}

interface ChatAdapterListener {
    fun onItemClick(item: Object)
}
