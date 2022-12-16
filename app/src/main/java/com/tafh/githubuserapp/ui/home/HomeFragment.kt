package com.tafh.githubuserapp.ui.home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.databinding.FragmentHomeBinding
import com.tafh.githubuserapp.ui.adapter.HomeAdapter


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.isEmpty.observe(viewLifecycleOwner) {
            showEmptyData(it)
        }

        val layoutManager: RecyclerView.LayoutManager
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvListUser.layoutManager = layoutManager

        homeViewModel.users.observe(viewLifecycleOwner) { users ->
            setUserData(users)
        }

        binding.swSearchUser.apply {
            isIconified = false

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.length >= 3) {
                        binding.rvListUser.scrollToPosition(0)
                        homeViewModel.querySearchUser(query)
                        clearFocus()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "minimal 3 karakter",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }

    }

    private fun setUserData(users: List<SearchItem>) {
        binding.rvListUser.apply {

            val usersAdapter = HomeAdapter(users)
            adapter = usersAdapter

            usersAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: SearchItem?) {
                    val actionToDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailUserFragment(
                        data?.login.toString(), data?.avatarUrl.toString(), data?.htmlUrl.toString()
                    )
                    findNavController().navigate(actionToDetailUser)
                }
            })

            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_MOVE -> binding.swSearchUser.clearFocus()
                    }
                    return v?.onTouchEvent(event) ?: true
                }

            })

        }
    }

    private fun setToolBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.apply {
            toolbarHome.setNavigationOnClickListener { it.findNavController().navigateUp() }
            toolbarHome.inflateMenu(R.menu.menu_home)
            toolbarHome.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.action_favorite -> {
                            moveToFavorite()
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            })
        }
    }

    private fun moveToFavorite() {
        val actionToFavorite = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
        findNavController().navigate(actionToFavorite)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarHome.visibility = View.VISIBLE
            binding.tvEmptySearch.visibility = View.GONE
        } else {
            binding.progressBarHome.visibility = View.GONE
            binding.rvListUser.visibility = View.VISIBLE
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvNofoundSearch.visibility = View.VISIBLE
            binding.rvListUser.visibility = View.GONE
        } else {
            binding.tvNofoundSearch.visibility = View.GONE
            binding.tvEmptySearch.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
