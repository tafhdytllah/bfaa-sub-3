package com.tafh.githubuserapp.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tafh.githubuserapp.ui.fragment.FollowerFragment
import com.tafh.githubuserapp.ui.fragment.FollowingFragment
import com.tafh.githubuserapp.ui.fragment.RepositoryFragment

class SectionPagerAdapter(activity: AppCompatActivity, val username: String) :
    FragmentStateAdapter(activity) {

    companion object {
        const val ARG_USERNAME_DETAIL = "username_detail"
    }

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = RepositoryFragment()
            1 -> fragment = FollowerFragment()
            2 -> fragment = FollowingFragment()
        }

        fragment?.arguments = Bundle().apply {
            putString(ARG_USERNAME_DETAIL, username)
        }

        return fragment as Fragment
    }
}