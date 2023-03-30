package com.edts.tmdroid.ui.person.list

import com.edts.tmdroid.databinding.ItemPersonBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Person

class PersonListAdapter(
    onClick: (Person) -> Unit,
) : BaseListAdapter<Person, ItemPersonBinding>(
    inflate = ItemPersonBinding::inflate,
    onClick = onClick,
    differ = Person.DIFFER,
) {

    override fun ItemPersonBinding.bind(item: Person) {
        ivProfile.loadFromUrl(item.profileUrl)
        tvName.text = item.name
    }
}
