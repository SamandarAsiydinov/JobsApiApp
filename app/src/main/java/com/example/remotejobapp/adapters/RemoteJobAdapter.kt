package com.example.remotejobapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.remotejobapp.databinding.ItemLayoutBinding
import com.example.remotejobapp.fragments.MainFragmentDirections
import com.example.remotejobapp.model.Job

class RemoteJobAdapter : ListAdapter<Job, RemoteJobAdapter.RemoteJobViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        return RemoteJobViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RemoteJobViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.apply {
                Glide.with(ivCompanyLogo)
                    .load(job.companyLogoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivCompanyLogo)

                tvCompanyName.text = job.companyName
                tvJobLocation.text = job.candidateRequiredLocation
                tvJobTitle.text = job.title
                tvJobType.text = job.jobType

                val dateJob = job.publicationDate?.split("T")
                tvDate.text = dateJob?.get(0)
            }
            itemView.setOnClickListener {
                val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job)
                it.findNavController().navigate(direction)
            }
        }
    }
}