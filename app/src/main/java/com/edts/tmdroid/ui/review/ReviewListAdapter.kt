package com.edts.tmdroid.ui.review

import com.edts.tmdroid.databinding.ItemReviewBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.model.Review

class ReviewListAdapter(
    onClick: (Review) -> Unit,
) : BaseListAdapter<Review, ItemReviewBinding>(
    inflate = ItemReviewBinding::inflate,
    onClick = onClick,
    differ = Review.DIFFER,
) {

    override fun ItemReviewBinding.bind(item: Review) {
        tvName.text = item.name
        tvComment.text = item.comment
    }
}
