package com.tafh.githubuserapp.ui.favorite

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.databinding.FragmentFavoriteBinding
import com.tafh.githubuserapp.ui.adapter.FavoriteAdapter
import com.tafh.githubuserapp.ui.adapter.HomeAdapter
import com.tafh.githubuserapp.ui.home.HomeFragmentDirections
import com.tafh.githubuserapp.ui.home.HomeViewModel
import com.tafh.githubuserapp.ui.home.HomeViewModelFactory

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        val layoutManager: RecyclerView.LayoutManager
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvListUserFavorite.layoutManager = layoutManager

        favoriteViewModel.getFavoritedUsers().observe(viewLifecycleOwner) { favoritedUser ->
            if (favoritedUser != null) {
                setUserData(favoritedUser)
            }
        }
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.apply {
            toolbarFavorite.setNavigationOnClickListener { it.findNavController().navigateUp() }
            toolbarFavorite.inflateMenu(R.menu.menu_favorite)
            toolbarFavorite.setTitle(R.string.title_favorite)
            toolbarFavorite.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?) =
                    when (item?.itemId) {
                        R.id.menu_delete -> {
                            favoriteViewModel.deleteAll()
                            true
                        }
                        else -> false

                    }
            })
        }
    }

    private fun setUserData(favoritedUser: List<UserEntity>) {
        binding.rvListUserFavorite.apply {
            val favoriteAdapter = FavoriteAdapter(favoritedUser)
            adapter = favoriteAdapter

            favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserEntity?) {
                    val actionToDetailUser = FavoriteFragmentDirections.actionFavoriteFragmentToDetailUserFragment(
                        data?.username.toString(), data?.avatarUrl.toString(), data?.htmlUrl.toString()
                    )
                    findNavController().navigate(actionToDetailUser)
                }

            })
        }
    }


}