package com.tafh.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tafh.githubuserapp.data.remote.response.Repository
import com.tafh.githubuserapp.databinding.ItemRowRepositoryBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RepoAdapter(private val list: List<Repository>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemCLickCallback(onitemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onitemClickCallback
    }

    @SuppressLint("SimpleDateFormat")
    inner class RepoViewHolder(private val binding: ItemRowRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repository) {
            binding.apply {
                var formattedDate = ""
                val dataDate = item.updateAt.toString()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val dateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
                    formattedDate = LocalDate.parse(dataDate, dateTimeFormatter).toString()
                } else {
                    formattedDate = ""
                }

                tvNameItemRepository.text = item.name.toString()
                tvVisibilityItemRepo.text = item.visibility.toString()
                tvLanguageItemRepository.text = item.language
                tvUpdateAtItemRepository.text = formattedDate

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(
            ItemRowRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Repository)
    }

}