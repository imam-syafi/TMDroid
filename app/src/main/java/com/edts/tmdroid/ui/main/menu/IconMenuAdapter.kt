package com.edts.tmdroid.ui.main.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.edts.tmdroid.databinding.ItemHeaderBinding
import com.edts.tmdroid.databinding.ItemIconMenuBinding
import com.edts.tmdroid.ui.main.menu.GridItem.Header
import com.edts.tmdroid.ui.main.menu.GridItem.IconMenu

class IconMenuAdapter(
    private val itemList: List<GridItem>,
) : Adapter<IconMenuAdapter.IconMenuViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Header -> TYPE_HEADER
            is IconMenu -> TYPE_ICON
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconMenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding = when (viewType) {
            TYPE_HEADER -> ItemHeaderBinding::inflate
            TYPE_ICON -> ItemIconMenuBinding::inflate
            else -> throw IllegalArgumentException("Invalid type")
        }

        return IconMenuViewHolder(inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: IconMenuViewHolder, position: Int) {
        when (val item = itemList[position]) {
            is Header -> holder.bindHeader(item)
            is IconMenu -> holder.bindIcon(item)
        }
    }

    inner class IconMenuViewHolder(
        private val binding: ViewBinding,
    ) : ViewHolder(binding.root) {

        fun bindHeader(item: Header) = with(binding as ItemHeaderBinding) {
            title.text = item.title
        }

        fun bindIcon(item: IconMenu) = with(binding as ItemIconMenuBinding) {
            root.setOnClickListener {
                item.action()
            }

            icon.setImageResource(item.icon)
            title.text = item.title
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ICON = 1
    }
}
