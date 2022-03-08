package com.tanmay.repotracker.ui.DetailsFrag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.tanmay.repotracker.R
import com.tanmay.repotracker.adapters.ViewPagerAdapter
import com.tanmay.repotracker.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullName = args.fullName
        val name = args.name
        val description = args.description

        _binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            this.description.text = description
            repoName.text = name

            val adapter = fullName?.let { fullName ->
                ViewPagerAdapter(this@DetailsFragment, fullName, onBranchClick = { bName ->
                    navigateToCommitsFrag(bName, fullName)
                })
            }
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Branches"
                    }
                    1 -> tab.text = "Issues"
                }
            }.attach()

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteRepo -> {
                        // TODO: 1/14/2022 delete repo
                        Toast.makeText(context, "Yet To be Implemented", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.visitRepo -> {
                        val url = "https://github.com/$fullName"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }

            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun navigateToCommitsFrag(Bname: String?, fullName: String) {
        if (Bname != null) {
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToCommitsFragment2(Bname, fullName)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}