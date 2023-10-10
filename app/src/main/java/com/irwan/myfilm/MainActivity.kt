package com.irwan.myfilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irwan.myfilm.adapter.ListFilmAdapter
import com.irwan.myfilm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvFilm: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listFilmAdapter: ListFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = binding.rvList
        listFilmAdapter = ListFilmAdapter()
        rvFilm.adapter = listFilmAdapter
        rvFilm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.listFilm.observe(this, {data ->
            listFilmAdapter.setFilm(data)
        })
        mainViewModel.findFilm()

        mainViewModel.isLoading.observe(this, {loading->
            showLoading(loading)
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}