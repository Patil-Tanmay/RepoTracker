package com.tanmay.repotracker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tanmay.repotracker.ui.BranchFragment


class ViewPagerAdapter(
    fragment: Fragment, private val fullName: String,
    private val onBranchClick: (Bname: String?) -> Unit
) :
    FragmentStateAdapter(fragment) {

    companion object {
        const val BRANCHES = "Branches"
        const val ISSUES = "Issues"
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                BranchFragment(fullName, BRANCHES, onBranchClick = {
                    onBranchClick(it)
                })
            }
            1 -> {
                BranchFragment(fullName, ISSUES, onBranchClick = {
                    onBranchClick(null)
                })
            }

            else -> BranchFragment(fullName, null, onBranchClick =
            { onBranchClick(null) })
        }
    }
}