package com.example.remotejobapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remotejobapp.R
import com.example.remotejobapp.activity.MainActivity
import com.example.remotejobapp.adapters.RemoteJobAdapter
import com.example.remotejobapp.databinding.FragmentSearchJobsBinding
import com.example.remotejobapp.utils.Constants
import com.example.remotejobapp.utils.toast
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class SearchJobFragment : Fragment() {

    private var _binding: FragmentSearchJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var rvAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        rvAdapter = RemoteJobAdapter()

        if (Constants.isNetworkAvailable(requireContext())) {
            setupRv()
            searchJob()
        } else {
            toast("No internet connection")
        }
    }

    private fun setupRv() = binding.rvSearchJobs.apply {
        val divider = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        layoutManager = LinearLayoutManager(activity)
        adapter = rvAdapter
        addItemDecoration(divider)
    }

    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchJob(editable.toString())

                        viewModel.searchJobResult().observe(viewLifecycleOwner) {
                            if (it != null) {
                                rvAdapter.submitList(it.jobs)
                            }
                        }
                    }
                }
            }
        }
    }
}