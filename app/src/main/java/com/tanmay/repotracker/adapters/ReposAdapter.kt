package com.tanmay.repotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanmay.repotracker.data.RepoData
import com.tanmay.repotracker.databinding.ItemRepoBinding

class ReposAdapter constructor(
    val imgOnClick : (url:String) -> Unit,
    val rootOnClick : (repoData:RepoData) -> Unit
) : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<RepoData>() {
        override fun areItemsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ReposViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data : RepoData){
                binding.apply {
                    title.text = data.name
                    description.text = data.description
                    share.setOnClickListener {
                        imgOnClick(data.html_url)
                    }
                    root.setOnClickListener {
                        rootOnClick(data)
                    }
                }
            }
    }
}