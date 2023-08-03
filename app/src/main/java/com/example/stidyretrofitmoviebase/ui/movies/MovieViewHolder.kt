package com.example.stidyretrofitmoviebase.ui.movies

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.databinding.ListItemMovieBinding
import com.example.stidyretrofitmoviebase.domain.models.Movie

class MovieViewHolder(
    private val binding: ListItemMovieBinding, private val clickListener: MoviesAdapter.MovieClickListener
) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(movie: Movie) {
        Glide.with(itemView).load(movie.image).into(binding.cover)

        binding.title.text = movie.title
        binding.description.text = movie.description

        binding.favorite.setImageDrawable(getFavoriteToggleDrawable(movie.inFavorite))

        itemView.setOnClickListener { clickListener.onMovieClick(movie) }
        binding.favorite.setOnClickListener { clickListener.onFavoriteToggleClick(movie) }
    }


    private fun getFavoriteToggleDrawable(inFavorite: Boolean): Drawable? {
        return itemView.context.getDrawable(
            if (inFavorite) R.drawable.like_button_like else R.drawable.like_button_no_like
        )
    }
}