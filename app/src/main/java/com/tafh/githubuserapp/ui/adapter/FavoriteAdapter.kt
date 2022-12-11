package com.tafh.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.databinding.ItemRowUserBinding

class FavoriteAdapter  {
//    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()
//    private val listFavorites = ArrayList<Favorite>()
//
//    fun setListFavorites(listFavorites: List<Favorite>) {
//        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        this.listFavorites.clear()
//        this.listFavorites.addAll(listFavorites)
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    inner class FavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(item: Favorite) {
//            with(binding) {
//                Glide.with(binding.root.context)
//                    .load(item.avatarUrl)
//                    .circleCrop()
//                    .placeholder(R.drawable.img_github_logo)
//                    .apply(RequestOptions().override(96, 96))
//                    .into(binding.ivItemAvatar)
//
//                tvItemName.text = item.login
//                tvItemUrl.text = item.htmlUrl
//
////                root.setOnClickListener {
////                    onItemClickCallback?.onItemClicked(item)
////                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
//        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return FavoriteViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
//        holder.bind(listFavorites[position])
//    }
//
//    override fun getItemCount(): Int {
//        return listFavorites.size
//    }


}