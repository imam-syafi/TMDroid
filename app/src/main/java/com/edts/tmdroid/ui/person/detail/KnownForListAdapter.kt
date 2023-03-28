package com.edts.tmdroid.ui.person.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edts.tmdroid.databinding.ItemKnownForBinding
import com.edts.tmdroid.ext.loadFromUrl
import com.edts.tmdroid.ui.model.KnownFor
import com.edts.tmdroid.ui.person.detail.KnownForListAdapter.KnownForViewHolder

class KnownForListAdapter(
    private val onClick: (KnownFor) -> Unit,
) : ListAdapter<KnownFor, KnownForViewHolder>(KnownFor.DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemKnownForBinding.inflate(inflater, parent, false)
        return KnownForViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KnownForViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class KnownForViewHolder(
        private val binding: ItemKnownForBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var knownFor: KnownFor? = null

        init {
            itemView.setOnClickListener {
                knownFor?.let(onClick)
            }
        }

        fun bind(item: KnownFor) = with(binding) {
            knownFor = item

            ivPoster.loadFromUrl(item.posterUrl)
            tvTitle.text = item.title
        }
    }
}
