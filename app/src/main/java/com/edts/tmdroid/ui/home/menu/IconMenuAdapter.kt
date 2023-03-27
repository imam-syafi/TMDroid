package com.edts.tmdroid.ui.home.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ItemHeaderBinding
import com.edts.tmdroid.databinding.ItemIconMenuBinding
import com.edts.tmdroid.ui.home.menu.GridItem.Header
import com.edts.tmdroid.ui.home.menu.GridItem.IconMenu

class IconMenuAdapter(
    private val itemList: List<GridItem>,
) : Adapter<IconMenuAdapter.IconMenuViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Header -> R.layout.item_header
            is IconMenu -> R.layout.item_icon_menu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconMenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding = when (viewType) {
            R.layout.item_header -> ItemHeaderBinding::inflate
            R.layout.item_icon_menu -> ItemIconMenuBinding::inflate
            else -> throw IllegalArgumentException("Invalid type")
        }
        val binding = inflate(inflater, parent, false)

        return IconMenuViewHolder(binding)
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
            title.setText(item.title)
        }

        fun bindIcon(item: IconMenu) = with(binding as ItemIconMenuBinding) {
            itemView.setOnClickListener {
                item.onClick()
            }

            icon.setImageResource(item.icon)
            title.setText(item.title)
        }
    }
}
