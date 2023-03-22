package com.edts.tmdroid.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.databinding.ItemMovieBinding
import com.edts.tmdroid.ext.loadFromUrl

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(Movie.DIFFER) {

    var delegate: MovieDelegate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding,
    ) : ViewHolder(binding.root) {

        fun bind(item: Movie) = with(binding) {
            val resources = itemView.resources

            ivPoster.loadFromUrl(item.posterUrl)
            tvTitle.text = item.title
            tvOverview.text = item.overview
            tvRating.text = resources.getString(
                R.string.rating,
                item.voteAverage.toString(),
                item.voteCount
            )

            itemView.setOnClickListener {
                delegate?.onMovieClicked(item)
            }
        }
    }
}
