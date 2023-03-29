package com.edts.tmdroid.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ItemMovieBinding
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Movie

class MovieListAdapter(
    private val onClick: (Movie) -> Unit,
) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(Movie.DIFFER) {

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

        private var movie: Movie? = null

        init {
            itemView.setOnClickListener {
                movie?.let(onClick)
            }
        }

        fun bind(item: Movie) = with(binding) {
            movie = item
            val resources = itemView.resources

            ivPoster.loadFromUrl(item.posterUrl)
            tvTitle.text = item.title
            tvOverview.text = item.overview
            tvRating.text = resources.getString(
                R.string.rating,
                item.voteAverage.toString(),
                item.voteCount,
            )
        }
    }
}
