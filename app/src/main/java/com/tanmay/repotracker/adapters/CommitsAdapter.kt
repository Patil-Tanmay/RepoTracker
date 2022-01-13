package com.tanmay.repotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanmay.repotracker.R
import com.tanmay.repotracker.data.BranchItem
import com.tanmay.repotracker.data.Commit
import com.tanmay.repotracker.data.CommitItem
import com.tanmay.repotracker.databinding.ItemCommitBinding

class CommitsAdapter : RecyclerView.Adapter<CommitsAdapter.CommitViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<CommitItem>() {
        override fun areItemsTheSame(oldItem: CommitItem, newItem: CommitItem): Boolean {
            return oldItem.sha == newItem.sha
        }

        override fun areContentsTheSame(oldItem: CommitItem, newItem: CommitItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
       return CommitViewHolder(ItemCommitBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        if (currentItem!=null) holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CommitViewHolder(private val binding: ItemCommitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : CommitItem){
            binding.commitId.text = data.sha
            binding.commitMessage.text = data.commit.message
            binding.date.text = data.commit.committer?.date
            binding.userName.text = data.commit.committer?.name
            Glide.with(itemView)
                .load(data.committer?.avatar_url)
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop().into(binding.imgView)
        }
    }

}