package com.tafh.githubuserapp.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.ui.adapter.SectionPagerAdapter.Companion.ARG_USERNAME_DETAIL
import com.tafh.githubuserapp.ui.adapter.HomeAdapter
import com.tafh.githubuserapp.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

//    private val factory: DetailUserViewModelFactory = DetailUserViewModelFactory.getInstance(requireActivity())
//    private val followingViewModel: DetailUserViewModel by viewModels {
//        factory
//    }

    private val followingViewModel: FollowingViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.isEmpty.observe(viewLifecycleOwner) {
            showEmptyData(it)
        }

        val username = arguments?.getString(ARG_USERNAME_DETAIL, "").toString()
        followingViewModel.getUserFollowing(username)

        followingViewModel.userFollowing.observe(viewLifecycleOwner) { listUser ->
            setUserRecyclerView(listUser)
        }
    }

    private fun setUserRecyclerView(listUser: List<SearchItem>) {
        binding.rvListUserFollowing.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            homeAdapter = HomeAdapter(listUser)
            adapter = homeAdapter

            homeAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: SearchItem?) {
                    Toast.makeText(
                        context,
                        data?.login,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarFollowing.visibility = View.VISIBLE
                tvEmptyFollowing.visibility = View.GONE
            } else {
                progressBarFollowing.visibility = View.GONE
                rvListUserFollowing.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                tvEmptyFollowing.visibility = View.VISIBLE
                rvListUserFollowing.visibility = View.GONE
            } else {
                tvEmptyFollowing.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}