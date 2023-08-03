package com.example.stidyretrofitmoviebase.ui.names

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stidyretrofitmoviebase.databinding.ListItemNamesBinding
import com.example.stidyretrofitmoviebase.domain.models.Names

class NamesAdapter() : RecyclerView.Adapter<NamesViewHolder>() {
    val names = mutableListOf<Names>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NamesViewHolder(ListItemNamesBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.bind(names[position])
    }
}