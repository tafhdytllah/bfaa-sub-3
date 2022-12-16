package com.tafh.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.databinding.ItemRowUserBinding

class FavoriteAdapter(private val list: List<UserEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class FavoriteViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserEntity) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.img_github_logo)
                    .apply(RequestOptions().override(96, 96))
                    .into(binding.ivItemAvatar)

                tvItemName.text = item.username
                tvItemUrl.text = item.htmlUrl

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity?)
    }
}