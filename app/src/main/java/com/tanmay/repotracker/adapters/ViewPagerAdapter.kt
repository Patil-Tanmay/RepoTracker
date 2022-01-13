package com.tanmay.repotracker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tanmay.repotracker.ui.BranchFragment
import com.tanmay.repotracker.ui.ReposFragment
import dagger.hilt.android.AndroidEntryPoint


class ViewPagerAdapter(fragment: Fragment, private val fullName: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                BranchFragment(fullName)
            }
            1 -> {
                BranchFragment(fullName)
            }

            else -> BranchFragment(fullName)
        }
    }
}