package com.irwan.myfilm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.irwan.myfilm.databinding.ListFilmBinding
import com.irwan.myfilm.response.ResultsItem

class ListFilmAdapter :
    PagingDataAdapter<ResultsItem, ListFilmAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(var binding: ListFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(films: ResultsItem) {
            binding.judul.text = films.title
            binding.tanggal.text = films.releaseDate
            binding.popular.text = films.popularity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListFilmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val films = getItem(position)
        if (films !=null){
            holder.bind(films)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ResultsItem> =
            object : DiffUtil.ItemCallback<ResultsItem>() {
                override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}