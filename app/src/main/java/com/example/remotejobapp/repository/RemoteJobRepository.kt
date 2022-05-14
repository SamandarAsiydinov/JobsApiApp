package com.example.remotejobapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remotejobapp.api.RetrofitInstance
import com.example.remotejobapp.model.RemoteJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJob?> = MutableLiveData()

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

    fun remoteJobResult(): LiveData<RemoteJob?> {
        return remoteJobResponseLiveData
    }
}