package com.example.remotejobapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remotejobapp.databinding.ItemLayoutBinding
import com.example.remotejobapp.fragments.MainFragmentDirections
import com.example.remotejobapp.model.Job
import com.example.remotejobapp.model.JobToSave

class RemoteJobSavedAdapter :
    ListAdapter<JobToSave, RemoteJobSavedAdapter.RemoteJobSavedViewHolder>(DiffCallBack()) {

    lateinit var onItemClick: (JobToSave) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<JobToSave>() {
        override fun areItemsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobSavedViewHolder {
        return RemoteJobSavedViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RemoteJobSavedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RemoteJobSavedViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobToSave) {
            binding.apply {
                Glide.with(ivCompanyLogo)
                    .load(job.companyLogoUrl)
                    .into(ivCompanyLogo)

                tvCompanyName.text = job.companyName
                tvJobLocation.text = job.candidateRequiredLocation
                tvJobTitle.text = job.title
                tvJobType.text = job.jobType
                btnDelete.isVisible = true

                val dateJob = job.publicationDate?.split("T")
                tvDate.text = dateJob?.get(0)

                btnDelete.setOnClickListener {
                    onItemClick.invoke(job)
                }
            }
            val jobs = Job(
                job.candidateRequiredLocation,
                job.category,
                job.companyLogoUrl,
                job.companyName,
                job.description,
                job.id,
                job.jobType,
                job.publicationDate,
                job.salary,
                arrayListOf(),
                job.title,
                job.url
            )
            itemView.setOnClickListener {
                val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(jobs)
                it.findNavController().navigate(direction)
            }
        }
    }
}