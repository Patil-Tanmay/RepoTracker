package com.tanmay.repotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanmay.repotracker.data.BranchItem
import com.tanmay.repotracker.data.IssuesItem
import com.tanmay.repotracker.databinding.ItemIssueBinding

class IssueAdapter : RecyclerView.Adapter<IssueAdapter.IssueVieHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<IssuesItem>() {
        override fun areItemsTheSame(oldItem: IssuesItem, newItem: IssuesItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IssuesItem, newItem: IssuesItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueVieHolder{
        return IssueVieHolder(ItemIssueBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IssueVieHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem!=null){
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class IssueVieHolder(private val binding : ItemIssueBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : IssuesItem){
            Glide.with(itemView)
                .load(data.user.avatar_url)
                .circleCrop()
                .into(binding.imgView)

            binding.name.text = data.user.login
            binding.title.text = data.title
        }
    }
}