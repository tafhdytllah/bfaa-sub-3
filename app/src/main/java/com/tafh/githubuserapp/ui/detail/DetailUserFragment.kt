package com.tafh.githubuserapp.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
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
import com.tafh.githubuserapp.ui.adapter.SectionPagerAdapter
import com.tafh.githubuserapp.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment(R.layout.fragment_detail_user) {

    private val TAG = "DetailUserFragment"
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2, R.string.tab_3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailUserViewModel.userDetail.observe(viewLifecycleOwner) { user ->
            binding.apply {
                toolbarDetail.title = user.login ?: ""

                Glide.with(requireContext())
                    .load(user.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.img_github_logo)
                    .into(ivAvatarDetailUser)

                tvNameDetailUser.text = user.name ?: "-"
                tvRepositoryDetailUser.text = (user.repositories ?: 0).toString()
                tvFollowerDetailUser.text = (user.followers ?: 0).toString()
                tvFollowingDetailUser.text = (user.following ?: 0).toString()
                tvLocationDetailUser.text = user.location ?: "-"
                tvCompanyDetailUser.text = user.company ?: "-"

            }
        }

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).usernameDetail
        subscribeData(username)

        val sectionPagerAdapter = SectionPagerAdapter(activity as AppCompatActivity, username)
        val viewPager: ViewPager2 = (activity as AppCompatActivity).findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = (activity as AppCompatActivity).findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f

        binding.apply {
            btnFollowDetailUser.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    btnFollowDetailUser.isChecked = false
                    btnFollowDetailUser.apply {
                        setTextColor(Color.parseColor("#FFFFFFFF"))
                        setBackgroundResource(R.drawable.bg_follow)
                    }
                } else {
                    btnFollowDetailUser.isChecked = true
                    btnFollowDetailUser.apply {
                        setTextColor(Color.parseColor("#FF000000"))
                        setBackgroundResource(R.drawable.bg_unfollow)
                    }
                }
            }
        }

    }

    private fun subscribeData(username: String) {
        detailUserViewModel.getDetailUser(username)
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

}

