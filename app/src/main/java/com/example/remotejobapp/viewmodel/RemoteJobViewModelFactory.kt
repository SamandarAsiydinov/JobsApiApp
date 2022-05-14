package com.example.remotejobapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remotejobapp.repository.RemoteJobRepository

@Suppress("UNCHECKED_CAST")
class RemoteJobViewModelFactory(
    private val application: Application,
    private val remoteJobRepository: RemoteJobRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(application, remoteJobRepository) as T
    }
}