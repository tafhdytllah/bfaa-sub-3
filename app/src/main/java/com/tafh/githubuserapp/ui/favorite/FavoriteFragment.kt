package com.tafh.githubuserapp.ui.favorite

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

//    private lateinit var favoriteAdapter: FavoriteAdapter

//    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
//        val factory = FavoriteViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarFavorite.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

//        val favoriteViewModel = obtainViewModel(requireContext() as AppCompatActivity)

        val layoutManager: RecyclerView.LayoutManager
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            layoutManager = LinearLayoutManager(requireContext())
        }

//        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner) { favoriteList ->
//            if (favoriteList != null) {
//                favoriteAdapter.setListFavorites(favoriteList)
//            } else {
//
//            }
//        }

//        favoriteAdapter = FavoriteAdapter()
//        binding.apply {
//            rvListUserFavorite.layoutManager = layoutManager
//            rvListUserFavorite.setHasFixedSize(true)
//            rvListUserFavorite.adapter = favoriteAdapter
//        }


    }



}