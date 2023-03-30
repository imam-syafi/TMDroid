package com.edts.tmdroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edts.tmdroid.databinding.ItemQueueBinding
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.home.WatchListAdapter.QueueViewHolder
import com.edts.tmdroid.ui.model.Queue

class WatchListAdapter(
    private val onClick: (Queue) -> Unit,
) : ListAdapter<Queue, QueueViewHolder>(Queue.DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQueueBinding.inflate(inflater, parent, false)
        return QueueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class QueueViewHolder(
        private val binding: ItemQueueBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var queue: Queue? = null

        init {
            itemView.setOnClickListener {
                queue?.let(onClick)
            }
        }

        fun bind(item: Queue) = with(binding) {
            queue = item

            ivPoster.loadFromUrl(item.posterUrl)
            tvTitle.text = item.title
        }
    }
}
