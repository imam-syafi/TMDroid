package com.edts.tmdroid.ui.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edts.tmdroid.R
import com.edts.tmdroid.ui.media.list.MediaListFragment
import com.edts.tmdroid.ui.media.list.MediaListFragmentArgs
import com.edts.tmdroid.ui.model.MediaListType
import com.edts.tmdroid.ui.model.PersonListType
import com.edts.tmdroid.ui.person.list.PersonListFragment
import com.edts.tmdroid.ui.person.list.PersonListFragmentArgs

class SearchPagerAdapter(
    private val query: String,
    private val titles: List<Int>,
    private val fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        val id = titles[position]
        val title = fragment.getString(id)

        return when (id) {
            R.string.movies -> {
                val args = MediaListFragmentArgs(
                    title = title,
                    mediaListType = MediaListType.SearchMovie(query),
                )

                MediaListFragment().apply {
                    arguments = args.toBundle()
                }
            }
            R.string.tv_shows -> {
                val args = MediaListFragmentArgs(
                    title = title,
                    mediaListType = MediaListType.SearchTv(query),
                )

                MediaListFragment().apply {
                    arguments = args.toBundle()
                }
            }
            R.string.people -> {
                val args = PersonListFragmentArgs(
                    title = title,
                    personListType = PersonListType.SearchPeople(query),
                )

                PersonListFragment().apply {
                    arguments = args.toBundle()
                }
            }
            else -> throw IllegalArgumentException("Invalid type")
        }
    }
}
