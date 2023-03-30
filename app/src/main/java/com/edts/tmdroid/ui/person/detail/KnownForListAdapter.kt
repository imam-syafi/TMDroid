package com.edts.tmdroid.ui.person.detail

import com.edts.tmdroid.databinding.ItemKnownForBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.KnownFor

class KnownForListAdapter(
    onClick: (KnownFor) -> Unit,
) : BaseListAdapter<KnownFor, ItemKnownForBinding>(
    inflate = ItemKnownForBinding::inflate,
    onClick = onClick,
    differ = KnownFor.DIFFER,
) {

    override fun ItemKnownForBinding.bind(item: KnownFor) {
        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = item.title
    }
}
