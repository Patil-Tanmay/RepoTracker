package com.tanmay.repotracker.ui.Branch

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.BranchAdapter
import com.tanmay.repotracker.adapters.IssueAdapter
import com.tanmay.repotracker.adapters.ViewPagerAdapter.Companion.BRANCHES
import com.tanmay.repotracker.adapters.ViewPagerAdapter.Companion.ISSUES
import com.tanmay.repotracker.databinding.FragmentBranchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BranchFragment(
    private val fullName: String,
    private val tabName: String? = null,
    val onBranchClick: (Bname: String?) -> Unit
) : Fragment(R.layout.fragment_branch) {

    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<BranchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBranchBinding.bind(view)



        if (tabName == BRANCHES) {
            val branchAdapter = BranchAdapter(::onRooClickListener)
            binding.apply {
                recView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recView.adapter = branchAdapter
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getBranches(fullName).collect {
                            branchAdapter.differ.submitList(it)
                        }
                    }
                    launch {
                        viewModel.exception.collect {
                            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else if (tabName == ISSUES) {
            val issueAdapter = IssueAdapter()
            binding.apply {
                recView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recView.adapter = issueAdapter
            }

            viewLifecycleOwner.lifecycleScope.launch {

                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getIssues(fullName).collect {
                            issueAdapter.differ.submitList(it)
                        }
                    }
                    launch {
                        viewModel.exception.collect {
                            Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                            Log.d("TAGG", "onViewCreated: $it")
                        }
                    }
                }
            }

        }


    }

    fun onRooClickListener(name: String) {
        // TODO: 1/13/2022 navigate to commits
        onBranchClick(name)
    }
}