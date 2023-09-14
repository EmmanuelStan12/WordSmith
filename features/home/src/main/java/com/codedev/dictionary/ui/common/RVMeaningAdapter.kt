package com.codedev.dictionary.ui.common

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedev.base.HTMLParser
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Meaning
import com.codedev.home.databinding.LayoutMeaningItemBinding
import com.codedev.home.databinding.LayoutWordItemBinding
import timber.log.Timber

class RVMeaningAdapter(
    private val itemClickListener: (Meaning) -> Unit
): RecyclerView.Adapter<RVMeaningAdapter.RVMeaningViewHolder>() {

    val diffList = AsyncListDiffer<Meaning>(this, object: DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.meaning_id == newItem.meaning_id
        }

        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVMeaningViewHolder {
        return RVMeaningViewHolder(
            LayoutMeaningItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RVMeaningViewHolder, position: Int) {
        val meaning = diffList.currentList[position]
        holder.bind(meaning)
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    inner class RVMeaningViewHolder(
        private val binding: LayoutMeaningItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(meaning: Meaning) {

            binding.tvPartOfSpeech.text = meaning.part_of_speech

            binding.tvWord.text = meaning.word

            meaning.meaning_synonyms.let { str ->
                if (str.isNotBlank()) {
                    var synonyms = str.split(",").joinToString(",") {
                        "<b>$it</b>"
                    }
                    synonyms = "<em>Synonyms:</em> $synonyms"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        binding.tvMeaningSynonyms.text =
                            Html.fromHtml(synonyms, Html.FROM_HTML_MODE_COMPACT);
                    } else
                        binding.tvMeaningSynonyms.text = Html.fromHtml(synonyms)
                }
                else binding.tvMeaningSynonyms.toggleVisibility(false)
            }

            meaning.meaning_antonyms.let { str ->
                if (str.isNotBlank()) {
                    var antonyms = str.split(",").joinToString(",") {
                        "<b>$it</b>"
                    }
                    antonyms = "<em>Antonyms:</em> $antonyms"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        binding.tvMeaningAntonyms.text =
                            Html.fromHtml(antonyms, Html.FROM_HTML_MODE_COMPACT);
                    } else
                        binding.tvMeaningAntonyms.text = Html.fromHtml(antonyms)
                }
                else binding.tvMeaningAntonyms.toggleVisibility(false)
            }

            val definitionAdapter = RVDefinitionAdapter({})

            binding.rvDefinitions.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                setHasFixedSize(true)
                adapter = definitionAdapter
            }

            definitionAdapter.diffList.submitList(meaning.definitions)

            binding.root.setOnClickListener {
                itemClickListener(meaning)
            }
        }
    }
}