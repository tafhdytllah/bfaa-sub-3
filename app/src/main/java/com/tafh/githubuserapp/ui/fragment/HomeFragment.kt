package com.tafh.githubuserapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.adapters.UserAdapter
import com.tafh.githubuserapp.data.remote.response.User
import com.tafh.githubuserapp.databinding.FragmentHomeBinding
import com.tafh.githubuserapp.viewmodel.HomeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    companion object {
        private const val STATE_USER = "state_result"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        homeViewModel.isEmpty.observe(viewLifecycleOwner, {
            showEmptyData(it)
        })

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
                        subscribeData(query)

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


    private fun subscribeData(query: String) {
        binding.rvListUser.scrollToPosition(0)

        homeViewModel.querySearchUser(query)
    }

    private fun setUserData(users: List<User>) {
        binding.rvListUser.apply {

            val usersAdapter = UserAdapter(users)
            adapter = usersAdapter

            usersAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    val actionToDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailUserFragment(
                        data.login
                    )
                    findNavController().navigate(actionToDetailUser)
                }
            })

            setOnTouchListener(object : View.OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_MOVE -> binding.swSearchUser.clearFocus()
                    }
                    return v?.onTouchEvent(event) ?: true
                }

            })

        }
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
