package com.tanmay.repotracker.ui.ReposFrag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.ReposAdapter
import com.tanmay.repotracker.databinding.FragmentReposBinding
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ReposViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReposBinding.bind(view)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addRepo -> {
                    val action = ReposFragmentDirections.actionReposFragmentToAddRepoFragment()
                    findNavController().navigate(action)
                }
            }
            true
        }

        val adapter = ReposAdapter(imgOnClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share Link")
            startActivity(shareIntent)
        },
            rootOnClick = {
                val action = ReposFragmentDirections.actionReposFragmentToDetailsFragment(
                    it.full_name,
                    it.name,
                    it.description
                )
                findNavController().navigate(action)
            })

        binding.btnAddRepo.setOnClickListener {
            val action = ReposFragmentDirections.actionReposFragmentToAddRepoFragment()
            findNavController().navigate(action)
        }
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.repoDataStatus.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    pBar.visibility = View.VISIBLE
                                    addRepo.visibility = View.GONE
                                    recView.visibility = View.GONE
                                }
                                is Resource.Failure -> {
                                    pBar.visibility = View.GONE
                                    addRepo.visibility = View.VISIBLE
                                    recView.visibility = View.GONE
                                }

                                is Resource.Success -> {
                                    pBar.visibility = View.GONE
                                    addRepo.visibility = View.GONE
                                    recView.visibility = View.VISIBLE
                                }

                                else -> {
                                }
                            }
                        }
                    }

                    launch {
                        viewModel.getReposData().collect {
                            adapter.differ.submitList(it)
                            recView.adapter = adapter
                            recView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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