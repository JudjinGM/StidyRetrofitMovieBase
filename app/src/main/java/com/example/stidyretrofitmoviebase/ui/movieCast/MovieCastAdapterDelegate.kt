package com.example.stidyretrofitmoviebase.ui.movieCast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.stidyretrofitmoviebase.databinding.ListItemCastBinding
import com.example.stidyretrofitmoviebase.databinding.ListItemHeaderBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

    // Делегат для заголовков на экране состава участников
    fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
        { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.headerTextView.text = item.headerText
        }
    }


    fun movieCastPersonDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.PersonItem, RVItem, ListItemCastBinding>(
        { layoutInflater, root -> ListItemCastBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            if (item.data.image == null) {
                binding.actorImageView.isVisible = false
            } else {
                Glide.with(itemView)
                    .load(item.data.image)
                    .into(binding.actorImageView)
                binding.actorImageView.isVisible = true
            }

            binding.actorNameTextView.text = item.data.name
            binding.actorDescriptionTextView.text = item.data.description
        }
    }