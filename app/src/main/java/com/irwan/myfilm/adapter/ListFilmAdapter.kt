package com.irwan.myfilm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irwan.myfilm.databinding.ListFilmBinding
import com.irwan.myfilm.response.ResultsItem

class ListFilmAdapter :
    RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {

    private val sortedListFilm: MutableList<ResultsItem> = mutableListOf()

    class ListViewHolder(var binding: ListFilmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListFilmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = sortedListFilm[position]
        holder.binding.judul.text = result.title
        holder.binding.tanggal.text = result.releaseDate
        holder.binding.popular.text = result.popularity.toString()
    }

    override fun getItemCount(): Int = sortedListFilm.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(data: List<ResultsItem>){
        sortedListFilm.clear()
        sortedListFilm.addAll(data.sortedByDescending { it.popularity })
        notifyDataSetChanged()
    }
}