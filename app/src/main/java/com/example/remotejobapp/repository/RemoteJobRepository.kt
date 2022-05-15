package com.example.remotejobapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remotejobapp.api.RetrofitInstance
import com.example.remotejobapp.database.JobDatabase
import com.example.remotejobapp.model.JobToSave
import com.example.remotejobapp.model.RemoteJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db: JobDatabase) {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJob?> = MutableLiveData()
    private val searchJobResponseLiveData: MutableLiveData<RemoteJob?> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {
        remoteJobService.getRemoteJobResponse().enqueue(object : Callback<RemoteJob> {
            override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                if (response.isSuccessful) {
                    remoteJobResponseLiveData.postValue(response.body())
                } else {
                    remoteJobResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                Log.d(this.javaClass.name, "${t.message}")
                remoteJobResponseLiveData.postValue(null)
            }
        })
    }

    fun searchJobResponse(query: String?) {
        remoteJobService.searchJob(query).enqueue(object : Callback<RemoteJob> {
            override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                if (response.isSuccessful) {
                    searchJobResponseLiveData.postValue(response.body())
                } else {
                    searchJobResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                Log.d("@@@@@",t.message.toString())
                searchJobResponseLiveData.postValue(null)
            }
        })
    }

    fun remoteJobResult(): LiveData<RemoteJob?> {
        return remoteJobResponseLiveData
    }

    fun searchJobResult(): LiveData<RemoteJob?> {
        return searchJobResponseLiveData
    }

    suspend fun addJob(job: JobToSave) = db.jobDao().addFavoriteJob(job)
    suspend fun deleteJob(job: JobToSave) = db.jobDao().deleteFavJob(job)
    fun getAllFavJobs() = db.jobDao().getAllFavoriteJob()
}