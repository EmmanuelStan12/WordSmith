package com.codedev.dictionary.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codedev.base.launchInLifecycle
import com.codedev.dictionary.ui.common.RVHistoryAdapter
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Word
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.common.HomeFragmentActions
import com.codedev.dictionary.ui.common.HomeFragmentUIEvents
import com.codedev.dictionary.ui.common.MainViewModel
import com.codedev.dictionary.ui.common.RVMeaningAdapter
import com.codedev.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import com.codedev.home.R as home_R


class HomeFragment : Fragment(home_R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var wordAdapter: RVHistoryAdapter

    private lateinit var searchAdapter: ArrayAdapter<String>

    private lateinit var meaningAdapter: RVMeaningAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        initData()
        initViews()
    }

    private fun initViews() {
        binding.etSearch.setDropDownBackgroundResource(com.codedev.base.R.drawable.background_search_edit_text)

        setupSearchDropdown()
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )

        binding.rvSearches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            wordAdapter = RVHistoryAdapter { history ->
                val args = Bundle()
                args.putString("word", history.word.name)
                findNavController().navigate(home_R.id.action_nav_home_to_wordResultFragment, args)
            }
            adapter = wordAdapter

            addItemDecoration(dividerItemDecoration)
        }

        binding.rvMeaning.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            meaningAdapter = RVMeaningAdapter {

            }
            adapter = meaningAdapter

            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun setupSearchDropdown() {
        searchAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item, mutableListOf())
        binding.etSearch.apply {
            threshold = 1
            setAdapter(searchAdapter)
        }

        binding.etSearch.imeOptions = IME_ACTION_SEARCH
        binding.etSearch.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == IME_ACTION_SEARCH) {
                    val args = Bundle()
                    args.putString("word", binding.etSearch.text.toString())
                    findNavController().navigate(home_R.id.action_nav_home_to_wordResultFragment, args)
                    return true
                }
                return false
            }

        })

        binding.etSearch.setAdapter(searchAdapter)
        binding.etSearch.addTextChangedListener {
            viewModel.execute(HomeFragmentActions.SearchWord(it.toString()))
        }

        binding.etSearch.setOnItemClickListener { adapterView, view, i, l ->
            val selectedWord = viewModel.searchResults.value[i]
            val args = Bundle()
            args.putString("word", selectedWord)
            findNavController().navigate(home_R.id.action_nav_home_to_wordResultFragment, args)
        }
    }

    private fun updateSearchDropdown(words: List<String>) {
        searchAdapter.clear()
        searchAdapter.addAll(words)
        searchAdapter.notifyDataSetChanged()
    }

    private fun initData() {
        viewModel = (requireActivity() as HomeActivity).mainViewModel

        viewModel.execute(HomeFragmentActions.GetRandomWord)
        viewModel.execute(HomeFragmentActions.GetHistories(5))

        launchInLifecycle {
            launch {
                viewModel.events.collectLatest { event ->
                    when (event) {
                        is HomeFragmentUIEvents.GetRandomWordLoading -> {
                            binding.clWordContent.toggleVisibility(false)
                            binding.shimmerWordContent.toggleVisibility(true)
                        }
                        is HomeFragmentUIEvents.GetRandomWordResult -> {
                            Timber.e(event.toString())
                            binding.clWordContent.toggleVisibility(true)
                            binding.shimmerWordContent.toggleVisibility(false)
                            if (event.word != null) {
                                setWordContent(event.word)
                            }
                        }

                        is HomeFragmentUIEvents.GetHistoriesLoading -> {
                            binding.shimmerHistories.toggleVisibility(true)
                            binding.tvNoSearch.toggleVisibility(false)
                            binding.rvSearches.toggleVisibility(false)
                        }
                        is HomeFragmentUIEvents.GetHistoriesResult -> {
                            Timber.e(event.toString())
                            val histories = event.histories
                            if (histories.isNullOrEmpty()) {
                                binding.shimmerHistories.toggleVisibility(false)
                                binding.tvNoSearch.toggleVisibility(true)
                                binding.rvSearches.toggleVisibility(false)
                            } else {
                                binding.shimmerHistories.toggleVisibility(false)
                                binding.tvNoSearch.toggleVisibility(false)
                                binding.rvSearches.toggleVisibility(true)
                                setupHistoryData(histories)
                            }
                        }

                        else -> {}
                    }
                }
            }

            launch {
                viewModel.searchResults.collect { searches ->
                    updateSearchDropdown(searches)
                }
            }
        }
    }

    private fun setupHistoryData(histories: List<History>) {
        wordAdapter.diffList.submitList(histories)
    }

    private fun setWordContent(word: Word) {
        binding.tvAbbr.text = word.phonetic

        binding.tvWord.text = word.name
        binding.tvLicense.text = word.license

        if (word.origin.isNotBlank()) {
            binding.tvOrigin.toggleVisibility(true)
            binding.tvOriginLabel.toggleVisibility(true)
            binding.tvOrigin.text = word.origin
        }

        if (word.phonetics?.isNotEmpty() == true) {
            binding.tvLabelPronunciation.toggleVisibility(true)
            binding.tvPronunciation.toggleVisibility(true)
            binding.vDivider2.toggleVisibility(true)
            val builder = StringBuilder()
            word.phonetics?.mapIndexed { i, phonetic ->
                builder.append("\t ★ ${phonetic.text}")
                if (i != word.phonetics!!.size - 1) {
                    builder.append("\n")
                }
            }
            binding.tvPronunciation.text = builder.toString()
        }

        meaningAdapter.diffList.submitList(word.meanings)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}