package com.tanmay.repotracker.ui.CommitsFrag

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.CommitsAdapter
import com.tanmay.repotracker.databinding.FragmentCommitsBinding
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommitsFragment : Fragment(R.layout.fragment_commits) {

    private var _binding: FragmentCommitsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<CommitsFragmentArgs>()

    private val viewModel by viewModels<CommitsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCommitsBinding.bind(view)

        binding.toolbar.subtitle = args.branchName

        val adapter = CommitsAdapter()

        binding.apply {
            recView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recView.adapter = adapter

            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getALlCommits(args.branchName, args.fullName).collect {
                        adapter.differ.submitList(it)
                    }
                }

                launch {
                    viewModel.errors.collect {
                        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    viewModel.progress.collect {
                        when (it) {
                            is Resource.Success -> {
                                binding.recView.visibility = View.VISIBLE
                                binding.pBar.visibility = View.GONE
                            }

                            is Resource.Failure -> {
                                binding.recView.visibility = View.GONE
                                binding.pBar.visibility = View.GONE
                                Toast.makeText(context, "Failed to get Data", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is Resource.Loading -> {
                                binding.recView.visibility = View.GONE
                                binding.pBar.visibility = View.VISIBLE
                            }

                            else -> {
                            }

                        }
                    }
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}