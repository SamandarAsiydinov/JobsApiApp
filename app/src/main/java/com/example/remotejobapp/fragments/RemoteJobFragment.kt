package com.example.remotejobapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remotejobapp.activity.MainActivity
import com.example.remotejobapp.adapters.RemoteJobAdapter
import com.example.remotejobapp.databinding.FragmentRemoteJobBinding
import com.example.remotejobapp.utils.toast
import com.example.remotejobapp.viewmodel.RemoteJobViewModel

class RemoteJobFragment : Fragment() {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        remoteJobAdapter = RemoteJobAdapter()
        setupRv()

        fetchingData()

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false

        }
    }

    private fun setupRv() = binding.rvRemoteJobs.apply {
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        val decoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        addItemDecoration(decoration)
        adapter = remoteJobAdapter
    }

    private fun fetchingData() {
        viewModel.remoteJobResult().observe(viewLifecycleOwner) { list ->
            if (list != null) {
                remoteJobAdapter.submitList(list.jobs)
            } else {
                toast("Error fetching data")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}