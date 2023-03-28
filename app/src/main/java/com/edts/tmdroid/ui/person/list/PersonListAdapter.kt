package com.edts.tmdroid.ui.person.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edts.tmdroid.databinding.ItemPersonBinding
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Person
import com.edts.tmdroid.ui.person.list.PersonListAdapter.PersonViewHolder

class PersonListAdapter(
    private val onClick: (Person) -> Unit,
) : ListAdapter<Person, PersonViewHolder>(Person.DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PersonViewHolder(
        private val binding: ItemPersonBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var person: Person? = null

        init {
            itemView.setOnClickListener {
                person?.let(onClick)
            }
        }

        fun bind(item: Person) = with(binding) {
            person = item

            ivProfile.loadFromUrl(item.profileUrl)
            tvName.text = item.name
        }
    }
}
