package com.edts.tmdroid.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val onClick: (T) -> Unit,
    differ: ItemCallback<T>,
) : ListAdapter<T, BaseListAdapter<T, VB>.ItemViewHolder>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.bind(item)
    }

    abstract fun VB.bind(item: T)

    inner class ItemViewHolder(
        val binding: VB,
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }
    }
}
