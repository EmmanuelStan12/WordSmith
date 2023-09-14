package com.codedev.dictionary.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codedev.base.launchInLifecycle
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.History
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.common.RVHistoryAdapter
import com.codedev.dictionary.ui.common.HomeFragmentActions
import com.codedev.dictionary.ui.common.HomeFragmentUIEvents
import com.codedev.dictionary.ui.common.MainViewModel
import com.codedev.home.R
import com.codedev.home.databinding.FragmentHistoryBinding
import timber.log.Timber

class HistoryFragment: Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var wordAdapter: RVHistoryAdapter

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHistoryBinding.bind(view)

        initData()
        initViews()
    }

    private fun initViews() {



        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            wordAdapter = RVHistoryAdapter { history ->
                val args = Bundle()
                args.putString("word", history.word.name)
                findNavController().navigate(R.id.action_nav_history_to_wordResultFragment, args)
            }
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
            adapter = wordAdapter
        }
    }

    private fun initData() {
        viewModel = (requireActivity() as HomeActivity).mainViewModel
        viewModel.execute(HomeFragmentActions.GetHistories())

        launchInLifecycle {
            viewModel.events.collect { event ->
                when (event) {
                    is HomeFragmentUIEvents.GetHistoriesLoading -> {
                        binding.shimmerHistories.toggleVisibility(true)
                        binding.tvNoHistory.toggleVisibility(false)
                        binding.rvHistory.toggleVisibility(false)
                    }
                    is HomeFragmentUIEvents.GetHistoriesResult -> {
                        Timber.e(event.toString())
                        val histories = event.histories
                        if (histories.isNullOrEmpty()) {
                            binding.shimmerHistories.toggleVisibility(false)
                            binding.tvNoHistory.toggleVisibility(true)
                            binding.rvHistory.toggleVisibility(false)
                        } else {
                            binding.shimmerHistories.toggleVisibility(false)
                            binding.tvNoHistory.toggleVisibility(false)
                            binding.rvHistory.toggleVisibility(true)
                            setupHistoryData(histories)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupHistoryData(histories: List<History>) {
        wordAdapter.diffList.submitList(histories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}