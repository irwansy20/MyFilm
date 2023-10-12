package com.irwan.myfilm

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.irwan.myfilm.adapter.ListFilmAdapter
import com.irwan.myfilm.databinding.ActivityMainBinding
import com.irwan.myfilm.response.ResultsItem


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvFilm: RecyclerView
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var listFilmAdapter: ListFilmAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = binding.rvList
        listFilmAdapter = ListFilmAdapter()
        rvFilm.adapter = listFilmAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                listFilmAdapter.retry()
            }
        )
        rvFilm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainViewModel.film.observe(this, {
            listFilmAdapter.submitData(lifecycle, it)
        })

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val title = getString(R.string.notification_title)
        val message = getString(R.string.notification_message)

        sendNotification(title, message)

        checkNetworkConnection()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNetworkConnection() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork
        val isNetworkAvailable = networkCapabilities != null
        if (!isNetworkAvailable) {
            showNoNetworkSnackbar()
        }    }

    private fun showNoNetworkSnackbar() {
        val snackbar = Snackbar.make(
            binding.root,
            "Tidak terhubung ke internet. Periksa koneksi Anda.",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Tutup") { snackbar.dismiss() }
        snackbar.show()
    }

    private fun sendNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText(getString(R.string.notification_subtext))
            .setContentIntent(pendingIntent)
            .setTimeoutAfter(5000)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "film channel"
    }
}