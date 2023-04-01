package com.edts.tmdroid.ui.search

import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentSearchBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate,
) {

    private val args by navArgs<SearchFragmentArgs>()

    override fun FragmentSearchBinding.setup() {
        val titles = listOf(
            R.string.movies,
            R.string.tv_shows,
            R.string.people,
        )

        pager.adapter = SearchPagerAdapter(
            query = args.query,
            titles = titles,
            fragment = this@SearchFragment,
        )

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = getString(titles[position])
        }.attach()
    }
}
