package com.tanmay.repotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanmay.repotracker.data.BranchItem
import com.tanmay.repotracker.data.RepoData
import com.tanmay.repotracker.databinding.ItemBranchBinding
import dagger.hilt.android.AndroidEntryPoint

class BranchAdapter constructor(
    val onRootClick : (name:String) -> Unit
) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<BranchItem>() {
        override fun areItemsTheSame(oldItem: BranchItem, newItem: BranchItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BranchItem, newItem: BranchItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(ItemBranchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem !=null){
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class BranchViewHolder(private val binding: ItemBranchBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(data : BranchItem){
                binding.branchName.text = data.name
                binding.root.setOnClickListener {
                    onRootClick(data.name)
                }
            }
        }
}