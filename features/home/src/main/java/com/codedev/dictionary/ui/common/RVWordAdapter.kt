package com.codedev.dictionary.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codedev.data_lib.models.Word
import com.codedev.home.databinding.LayoutWordItemBinding

class RVWordAdapter(
    private val itemClickListener: (Word) -> Unit
): RecyclerView.Adapter<RVWordAdapter.RVWordViewHolder>() {

    val diffList = AsyncListDiffer<Word>(this, object: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVWordViewHolder {
        return RVWordViewHolder(
            LayoutWordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RVWordViewHolder, position: Int) {
        val history = diffList.currentList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    inner class RVWordViewHolder(
        private val binding: LayoutWordItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(history: Word) {
            binding.tvWord.text = history.name
            binding.tvAbbr.text = history.phonetic

            binding.root.setOnClickListener {
                itemClickListener(history)
            }
        }
    }
}