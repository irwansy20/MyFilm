package com.irwan.myfilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irwan.myfilm.adapter.ListFilmAdapter
import com.irwan.myfilm.database.Result
import com.irwan.myfilm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvFilm: RecyclerView
//    private lateinit var mainViewModel: MainViewModel
    private lateinit var listFilmAdapter: ListFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = binding.rvList
        listFilmAdapter = ListFilmAdapter()
        rvFilm.adapter = listFilmAdapter
        rvFilm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: MainViewModel by viewModels {
            factory
        }

        viewModel.getPop().observe(this, {result ->
            if (result != null){
                when (result){
                    is Result.Loading ->{
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding?.progressBar?.visibility = View.GONE
                        val filmData = result.data
                        listFilmAdapter.submitList(filmData)
                    }
                    is Result.Error ->{
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
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