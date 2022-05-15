package com.example.remotejobapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.remotejobapp.model.JobToSave

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteJob(job: JobToSave)

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllFavoriteJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteFavJob(job: JobToSave)
}