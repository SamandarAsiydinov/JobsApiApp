package com.example.remotejobapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.remotejobapp.activity.MainActivity
import com.example.remotejobapp.databinding.FragmentJobDetailsBinding
import com.example.remotejobapp.model.Job
import com.example.remotejobapp.model.JobToSave
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar

class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RemoteJobViewModel

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
        viewModel = (activity as MainActivity).viewModel
        job = args.job!!

        setupWebView()

        binding.fabAddFavorite.setOnClickListener {
            saveFavJob(it)
        }
    }

    private fun saveFavJob(view: View) {
        val favJob = JobToSave(
            0,
            job.candidateRequiredLocation,
            job.category,
            job.companyLogoUrl,
            job.companyName,
            job.description,
            job.id,
            job.jobType,
            job.publicationDate,
            job.salary,
            job.title,
            job.url
        )
        viewModel.addJob(favJob)
        Snackbar.make(view, "Job Saved Successfully", Snackbar.LENGTH_LONG).show()
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            job.url?.let {
                loadUrl(it)
            }

            settings.apply {
                javaScriptEnabled = true
                setAppCacheEnabled(true)
                cacheMode = WebSettings.LOAD_DEFAULT
                setSupportZoom(false)
                builtInZoomControls = false
                displayZoomControls = false
                textZoom = 100
                blockNetworkImage = false
                loadsImagesAutomatically = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}