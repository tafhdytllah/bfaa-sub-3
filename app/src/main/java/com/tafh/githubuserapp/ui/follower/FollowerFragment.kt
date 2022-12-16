package com.tafh.githubuserapp.ui.follower

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
import com.tafh.githubuserapp.databinding.FragmentFollowerBinding
import com.tafh.githubuserapp.ui.adapter.HomeAdapter

class FollowerFragment : Fragment(R.layout.fragment_follower) {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

//    private val factory: DetailUserViewModelFactory = DetailUserViewModelFactory.getInstance(requireActivity())
//    private val followerViewModel: DetailUserViewModel by viewModels {
//        factory
//    }
    private val followerViewModel: FollowerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followerViewModel.isEmpty.observe(viewLifecycleOwner) {
            showEmptyData(it)
        }

        val layoutManager = LinearLayoutManager(context)
        binding.rvListUserFollower.layoutManager = layoutManager

        followerViewModel.userFollower.observe(viewLifecycleOwner) { listUser ->
            setUserData(listUser)
        }

        val username = arguments?.getString(ARG_USERNAME_DETAIL, "").toString()
        subsscribeData(username)

    }

    private fun subsscribeData(username: String) {
        binding.rvListUserFollower.scrollToPosition(0)
        followerViewModel.getUserFollower(username)
    }

    private fun setUserData(listUser: List<SearchItem>) {
        binding.rvListUserFollower.apply {
            val userAdapter = HomeAdapter(listUser)
            adapter = userAdapter

            userAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
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
                progressBarFollower.visibility = View.VISIBLE
                tvEmptyFollower.visibility = View.GONE
            } else {
                progressBarFollower.visibility = View.GONE
                rvListUserFollower.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                tvEmptyFollower.visibility = View.VISIBLE
                rvListUserFollower.visibility = View.GONE
            } else {
                tvEmptyFollower.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}