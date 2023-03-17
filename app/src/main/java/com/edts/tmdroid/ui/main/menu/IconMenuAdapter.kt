package com.edts.tmdroid.ui.main.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edts.tmdroid.databinding.ItemIconMenuBinding

class IconMenuAdapter(
    private val itemList: List<IconMenu>,
) : Adapter<IconMenuAdapter.IconMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconMenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemIconMenuBinding.inflate(layoutInflater)
        return IconMenuViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: IconMenuViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class IconMenuViewHolder(
        private val binding: ItemIconMenuBinding,
    ) : ViewHolder(binding.root) {

        fun bind(item: IconMenu): Unit = with(binding) {
            root.setOnClickListener {
                item.action()
            }

            icon.setImageResource(item.icon)
            title.text = item.title
        }
    }
}
