package com.example.remotejobapp.api

import com.example.remotejobapp.model.RemoteJob
import com.example.remotejobapp.utils.Constants.END_POINT
import retrofit2.Call
import retrofit2.http.GET

interface RemoteJobResponse {

    @GET(END_POINT)
    fun getRemoteJobResponse(): Call<RemoteJob>
}