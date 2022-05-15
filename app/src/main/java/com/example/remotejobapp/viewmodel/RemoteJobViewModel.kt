package com.example.remotejobapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotejobapp.model.JobToSave
import com.example.remotejobapp.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {

    fun remoteJobResult() = remoteJobRepository.remoteJobResult()

    fun addJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.addJob(job)
    }

    fun deleteJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllJobs() = remoteJobRepository.getAllFavJobs()

    fun searchJob(query: String?) = remoteJobRepository.searchJobResponse(query)

    fun searchJobResult() = remoteJobRepository.searchJobResult()
}