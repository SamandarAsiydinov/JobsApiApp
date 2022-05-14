package com.example.remotejobapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.remotejobapp.databinding.FragmentJobDetailsBinding
import com.example.remotejobapp.model.Job

class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var job: Job
    private val args: JobDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        job = args.job!!
        setupWebView()
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            job.url?.let { loadUrl(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}