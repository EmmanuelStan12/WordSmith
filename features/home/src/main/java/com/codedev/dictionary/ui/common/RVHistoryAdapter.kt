package com.codedev.dictionary.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codedev.data_lib.models.History
import com.codedev.home.databinding.LayoutWordItemBinding

class RVHistoryAdapter(
    private val itemClickListener: (History) -> Unit
): RecyclerView.Adapter<RVHistoryAdapter.RVHistoryViewHolder>() {

    val diffList = AsyncListDiffer<History>(this, object: DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.word.name == newItem.word.name
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHistoryViewHolder {
        return RVHistoryViewHolder(
            LayoutWordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RVHistoryViewHolder, position: Int) {
        val history = diffList.currentList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    inner class RVHistoryViewHolder(
        private val binding: LayoutWordItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.tvWord.text = history.word.name
            binding.tvAbbr.text = history.word.phonetic

            binding.root.setOnClickListener {
                itemClickListener(history)
            }
        }
    }
}