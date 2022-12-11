package com.tafh.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.remote.response.User
import com.tafh.githubuserapp.databinding.ItemRowUserBinding

class UserAdapter(private val list: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class UserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.img_github_logo)
                    .apply(RequestOptions().override(96, 96))
                    .into(binding.ivItemAvatar)

                tvItemName.text = item.login
                tvItemUrl.text = item.htmlUrl

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}