package com.tanmay.repotracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.BranchAdapter
import com.tanmay.repotracker.databinding.FragmentBranchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BranchFragment(private val fullName :String) : Fragment(R.layout.fragment_branch) {

    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<BranchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBranchBinding.bind(view)

        val branchAdapter = BranchAdapter(::onRooClickListener)


        binding.apply {
            recView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recView.adapter = branchAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.getBranches(fullName).collect {
                    branchAdapter.differ.submitList(it)
                }
            }

            launch {
                viewModel.exception.collect {
                    Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    fun onRooClickListener(name: String){
        //todo navigate to commits branch
    }
}