package com.tafh.githubuserapp.ui.fragment

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
import com.tafh.githubuserapp.adapters.SectionPagerAdapter.Companion.ARG_USERNAME_DETAIL
import com.tafh.githubuserapp.adapters.RepoAdapter
import com.tafh.githubuserapp.data.remote.response.Repository
import com.tafh.githubuserapp.databinding.FragmentRepositoryBinding
import com.tafh.githubuserapp.viewmodel.DetailUserViewModel

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    companion object {
        private const val TAG = "RepositoryFragment"
    }

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private val repositoryViewModel by viewModels<DetailUserViewModel>()

    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        repositoryViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        repositoryViewModel.isEmpty.observe(viewLifecycleOwner) {
            showEmptyData(it)
        }


        val username = arguments?.getString(ARG_USERNAME_DETAIL, "").toString()
        repositoryViewModel.getUserRepo(username)

        repositoryViewModel.userRepo.observe(viewLifecycleOwner) { listRepo ->
            setRepoRecyclerView(listRepo)
        }

    }

    private fun setRepoRecyclerView(listRepo: List<Repository>) {
        binding.rvListRepo.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            repoAdapter = RepoAdapter(listRepo)
            adapter = repoAdapter

            repoAdapter.setOnItemCLickCallback(object : RepoAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Repository) {
                    Toast.makeText(
                        context,
                        data.name,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarRepository.visibility = View.VISIBLE
                tvEmptyRepository.visibility = View.GONE
            } else {
                progressBarRepository.visibility = View.GONE
                rvListRepo.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                tvEmptyRepository.visibility = View.VISIBLE
                rvListRepo.visibility = View.GONE
            } else {
                tvEmptyRepository.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}