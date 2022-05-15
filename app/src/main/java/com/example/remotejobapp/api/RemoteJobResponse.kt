package com.example.remotejobapp.api

import com.example.remotejobapp.model.RemoteJob
import com.example.remotejobapp.utils.Constants.END_POINT
import com.example.remotejobapp.utils.Constants.END_POINT_1
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobResponse {

    @GET(END_POINT)
    fun getRemoteJobResponse(): Call<RemoteJob>

    @GET(END_POINT_1)
    fun searchJob(@Query("search") query: String?): Call<RemoteJob>
}