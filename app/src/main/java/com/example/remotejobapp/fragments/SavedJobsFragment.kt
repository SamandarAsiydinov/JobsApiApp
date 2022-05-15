package com.example.remotejobapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remotejobapp.R
import com.example.remotejobapp.activity.MainActivity
import com.example.remotejobapp.adapters.RemoteJobSavedAdapter
import com.example.remotejobapp.databinding.FragmentSavedJobsBinding
import com.example.remotejobapp.model.JobToSave
import com.example.remotejobapp.utils.toast
import com.example.remotejobapp.viewmodel.RemoteJobViewModel

class SavedJobsFragment : Fragment() {

    private var _binding: FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var rvAdapter: RemoteJobSavedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        rvAdapter = RemoteJobSavedAdapter()

        rvAdapter.onItemClick = {
            deleteJob(it)
        }

        setupRv()
        initViewModel()
    }

    private fun setupRv() = binding.recyclerView.apply {
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        layoutManager = LinearLayoutManager(requireContext())
        adapter = rvAdapter
        addItemDecoration(divider)
    }

    private fun initViewModel() {
        viewModel.getAllJobs().observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                rvAdapter.submitList(list)
            }
            binding.apply {
                recyclerView.isVisible = list.isNotEmpty()
                cardNoAvailable.isVisible = list.isEmpty()
            }
        }
    }

    private fun deleteJob(job: JobToSave) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Do you want to delete this job?")
            setPositiveButton("Delete") { _, _ ->
                viewModel.deleteJob(job)
                toast("Deleted job")
            }
            setNeutralButton("Cancel", null)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}