package com.example.remotejobapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.remotejobapp.database.JobDatabase
import com.example.remotejobapp.databinding.ActivityMainBinding
import com.example.remotejobapp.repository.RemoteJobRepository
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import com.example.remotejobapp.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = ""

        setupViewModel()
    }

    private fun setupViewModel() {
        val remoteJobRepository = RemoteJobRepository(JobDatabase(this))
        val viewModelProviderFactory = RemoteJobViewModelFactory(
            application = application,
            remoteJobRepository = remoteJobRepository
        )
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[RemoteJobViewModel::class.java]
    }
}