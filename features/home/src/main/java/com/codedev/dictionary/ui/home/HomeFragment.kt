package com.codedev.dictionary.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.Word
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.home._di.HomeFeatureComponent
import com.codedev.home.R
import com.codedev.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var homeFeatureComponent: HomeFeatureComponent

    @Inject
    lateinit var homeViewModel: HomeViewModel

    /*private val viewModel: HomeViewModel by lazy {
        ViewModelProvider((requireActivity() as HomeActivity).viewModelStore,viewModelFactory)[HomeViewModel::class.java]
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeFeatureComponent = (requireActivity() as HomeActivity).homeComponent
            .homeFeatureComponent()
            .fragment(this)
            .build()

        homeFeatureComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        initData()
        initViews()
    }

    private fun initViews() {
        binding.etSearch.setDropDownBackgroundResource(com.codedev.base.R.drawable.background_search_edit_text)
    }

    private fun initData() {

        homeViewModel.execute(HomeFragmentActions.GetRandomWord)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.events.collectLatest { event ->
                        when (event) {
                            is HomeFragmentUIEvents.LoadingStarted -> {
                                binding.clWordContent.toggleVisibility(false)
                                binding.shimmerWordContent.toggleVisibility(true)
                            }
                            HomeFragmentUIEvents.LoadingStopped -> {
                                binding.clWordContent.toggleVisibility(true)
                                binding.shimmerWordContent.toggleVisibility(false)
                            }
                        }
                    }
                }

                launch {
                    homeViewModel.randomWord.collectLatest { word ->
                        if (word != null) {
                            setWordContent(word)
                        }
                    }
                }
            }
        }
    }

    private fun setWordContent(word: Word) {
        binding.tvWord.text = word.name
        binding.tvAbbr.text = word.phonetic
        binding.tvLicense.text = word.license
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}