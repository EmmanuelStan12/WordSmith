package com.codedev.dictionary.ui.favourite

import android.os.Bundle
import android.view.View
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
import com.codedev.home.databinding.FragmentFavouriteBinding
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private var _binding: FragmentFavouriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var wordAdapter: RVWordAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFavouriteBinding.bind(view)

        initData()
        initViews()
    }

    private fun initData() {
        viewModel = (requireActivity() as HomeActivity).mainViewModel

        viewModel.execute(HomeFragmentActions.GetFavourites)

        launchInLifecycle {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is HomeFragmentUIEvents.GetFavouritesLoading -> {
                        binding.shimmerFavourites.toggleVisibility(true)
                        binding.rvFavourites.toggleVisibility(false)
                    }
                    is HomeFragmentUIEvents.GetFavouritesResult -> {
                        Timber.e(event.toString())
                        val favourites = event.words
                        if (favourites.isNullOrEmpty()) {
                            binding.shimmerFavourites.toggleVisibility(false)
                            binding.rvFavourites.toggleVisibility(false)
                            binding.tvNoFavourites.toggleVisibility(true)
                        } else {
                            binding.shimmerFavourites.toggleVisibility(false)
                            binding.rvFavourites.toggleVisibility(true)
                            binding.tvNoFavourites.toggleVisibility(false)
                            wordAdapter.diffList.submitList(favourites)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initViews() {


        binding.rvFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            wordAdapter = RVWordAdapter { word ->
                val args = Bundle()
                args.putString("word", word.name)
                findNavController().navigate(R.id.action_nav_favourites_to_wordResultFragment, args)
            }
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
            adapter = wordAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}