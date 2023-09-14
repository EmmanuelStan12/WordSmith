package com.codedev.dictionary.ui.common

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codedev.base.HTMLParser
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Definition
import com.codedev.home.databinding.LayoutDefinitionItemBinding
import com.codedev.home.databinding.LayoutWordItemBinding

class RVDefinitionAdapter(
    private val itemClickListener: (Definition) -> Unit
): RecyclerView.Adapter<RVDefinitionAdapter.RVDefinitionViewHolder>() {

    val diffList = AsyncListDiffer<Definition>(this, object: DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem.definition_id == newItem.definition_id
        }

        override fun areContentsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVDefinitionViewHolder {
        return RVDefinitionViewHolder(
            LayoutDefinitionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RVDefinitionViewHolder, position: Int) {
        val definition = diffList.currentList[position]
        holder.bind(definition)
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    inner class RVDefinitionViewHolder(
        private val binding: LayoutDefinitionItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(definition: Definition) {

            binding.tvTitle.text = "● ${definition.definition}"

            definition.definition_example.let {
                if (it.isNotBlank()) binding.tvExamples.text = "\t- ex: $it"
                else binding.tvExamples.toggleVisibility(false)
            }

            binding.root.setOnClickListener {
                itemClickListener(definition)
            }
        }
    }
}