package com.codedev.dictionary.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codedev.base.launchInLifecycle
import com.codedev.base.toggleVisibility
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.common.RVWordAdapter
import com.codedev.dictionary.ui.common.HomeFragmentActions
import com.codedev.dictionary.ui.common.HomeFragmentUIEvents
import com.codedev.dictionary.ui.common.MainViewModel
import com.codedev.home.R
import com.codedev.home.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null

    private lateinit var viewModel: MainViewModel
    private val binding get() = _binding!!

    private lateinit var wordAdapter: RVWordAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.bind(view)

        initData()
        initViews()
    }

    private fun initData() {
        viewModel = (requireActivity() as HomeActivity).mainViewModel

        binding.rvSearches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            wordAdapter = RVWordAdapter { word ->
                val args = Bundle()
                args.putString("word", word.name)
                findNavController().navigate(R.id.action_nav_search_to_wordResultFragment, args)
            }
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
            adapter = wordAdapter

        }

        launchInLifecycle {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is HomeFragmentUIEvents.SearchWordResult -> {
                        Timber.e(event.toString())
                        if (event.words.isNotEmpty()) {
                            binding.tvNoSearch.toggleVisibility(false)
                            binding.rvSearches.toggleVisibility(true)
                            wordAdapter.diffList.submitList(event.words)
                        } else {
                            binding.tvNoSearch.toggleVisibility(true)
                            binding.rvSearches.toggleVisibility(false)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initViews() {
        binding.etSearch.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val args = Bundle()
                    args.putString("word", binding.etSearch.text.toString())
                    findNavController().navigate(R.id.action_nav_search_to_wordResultFragment, args)
                    return true
                }
                return false
            }

        })
        binding.etSearch.addTextChangedListener {
            viewModel.execute(HomeFragmentActions.SearchWordWithReturnType(it.toString()))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}