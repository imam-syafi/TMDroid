package com.edts.tmdroid.ui.person.list

import com.edts.tmdroid.databinding.ItemPersonBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Person

class PersonListAdapter(
    onClick: (Person) -> Unit,
    private val onLastItem: () -> Unit,
) : BaseListAdapter<Person, ItemPersonBinding>(
    inflate = ItemPersonBinding::inflate,
    onClick = onClick,
    differ = Person.DIFFER,
) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val isLastItem = position == itemCount - 1
        if (isLastItem) {
            onLastItem()
        }
    }

    override fun ItemPersonBinding.bind(item: Person) {
        ivProfile.loadFromUrl(item.profileUrl)
        tvName.text = item.name
    }
}
