package com.example.stidyretrofitmoviebase.ui.names

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.databinding.ListItemNamesBinding
import com.example.stidyretrofitmoviebase.domain.models.Names

class NamesViewHolder(private val binding: ListItemNamesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Names) {
        binding.name.text = model.title
        binding.description.text = model.description

        Glide.with(itemView).load(model.image).placeholder(R.drawable.ic_placeholder_person).circleCrop().into(binding.photo)
    }

}