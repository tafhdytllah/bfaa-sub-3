package com.tafh.githubuserapp.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.ui.adapter.SectionPagerAdapter
import com.tafh.githubuserapp.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment(R.layout.fragment_detail_user) {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userEntity: UserEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).login
        val avatarUrl = DetailUserFragmentArgs.fromBundle(arguments as Bundle).avatarUrl
        val htmlUrl = DetailUserFragmentArgs.fromBundle(arguments as Bundle).htmlUrl

        val factory: DetailUserViewModelFactory =
            DetailUserViewModelFactory.getInstance(requireActivity())
        val detailUserViewModel: DetailUserViewModel by viewModels {
            factory
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailUserViewModel.getUserByUsername(username).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                userEntity = user
                binding.btnFavoriteDetailUser.isChecked = user.isFavorited ?: false
            } else {
                userEntity = UserEntity(
                    username = username,
                    avatarUrl = avatarUrl,
                    htmlUrl = htmlUrl,
                    isFavorited = false
                )
                detailUserViewModel.insertUser(userEntity)
            }

        }

        detailUserViewModel.userDetail.observe(viewLifecycleOwner) { user ->
            binding.apply {

                val login = user.login ?: ""
                val avatar = user.avatarUrl
                val name = user.name ?: "-"
                val repository = (user.repositories ?: 0).toString()
                val follower = (user.followers ?: 0).toString()
                val following = (user.following ?: 0).toString()
                val location = user.location ?: "-"
                val company = user.company ?: "-"

                toolbarDetail.title = login

                Glide.with(requireContext())
                    .load(avatar)
                    .circleCrop()
                    .placeholder(R.drawable.img_github_logo)
                    .into(ivAvatarDetailUser)

                tvNameDetailUser.text = name
                tvRepositoryDetailUser.text = repository
                tvFollowerDetailUser.text = follower
                tvFollowingDetailUser.text = following
                tvLocationDetailUser.text = location
                tvCompanyDetailUser.text = company

            }
        }



        detailUserViewModel.getDetailUser(username)

        val sectionPagerAdapter = SectionPagerAdapter(activity as AppCompatActivity, username)
        val viewPager: ViewPager2 = (activity as AppCompatActivity).findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = (activity as AppCompatActivity).findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.apply {
            btnFavoriteDetailUser.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    userEntity.let { detailUserViewModel.deleteFavorite(it) }
                    btnFavoriteDetailUser.isChecked = isChecked
                } else {
                    userEntity.let { detailUserViewModel.setFavorite(it) }
                    btnFavoriteDetailUser.isChecked = isChecked
                }
            }
        }

    }

    private fun setToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.apply {
            toolbarDetail.setNavigationOnClickListener { it.findNavController().navigateUp() }
            toolbarDetail.inflateMenu(R.menu.menu_detail)
            toolbarDetail.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.action_share -> {
                            shareIntent()
                            return true
                        }
                        else -> {
                            return false
                        }
                    }
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarDetailUser.visibility = View.VISIBLE
            } else {
                progressBarDetailUser.visibility = View.GONE
            }
        }
    }

    private fun shareIntent() {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, """
                    Aplikasi Github User
                    Dibuat oleh Taufik Hidayatullah
                """.trimIndent()
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2, R.string.tab_3)
    }
}

