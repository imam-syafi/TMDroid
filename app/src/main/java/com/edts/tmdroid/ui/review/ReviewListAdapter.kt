package com.edts.tmdroid.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edts.tmdroid.databinding.ItemReviewBinding
import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.review.ReviewListAdapter.ReviewViewHolder

class ReviewListAdapter(
    private val onClick: (Review) -> Unit,
) : ListAdapter<Review, ReviewViewHolder>(Review.DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding,
    ) : ViewHolder(binding.root) {

        private var review: Review? = null

        init {
            itemView.setOnClickListener {
                review?.let(onClick)
            }
        }

        fun bind(item: Review) = with(binding) {
            review = item

            tvName.text = item.name
            tvComment.text = item.comment
        }
    }
}
