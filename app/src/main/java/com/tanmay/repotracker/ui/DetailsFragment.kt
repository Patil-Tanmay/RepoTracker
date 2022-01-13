package com.tanmay.repotracker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.ViewPagerAdapter
import com.tanmay.repotracker.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding : FragmentDetailsBinding?=null
    private val binding get() = _binding!!

    private val args:DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullName = args.fullName
        val name = args.name
        val description = args.description

        _binding = FragmentDetailsBinding.bind(view)

        binding.description.text = description
        binding.repoName.text = name

        val adapter = fullName?.let { ViewPagerAdapter(this, it) }
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0-> {
                    tab.text = "Branches"
                }
                1-> tab.text = "Issues"
            }
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}