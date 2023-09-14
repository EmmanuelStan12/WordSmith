package com.codedev.dictionary.ui.exit

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
import com.codedev.home.databinding.FragmentExitBinding
import com.codedev.home.databinding.FragmentFavouriteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class ExitFragment : BottomSheetDialogFragment(R.layout.fragment_exit) {

    private var _binding: FragmentExitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentExitBinding.bind(view)

        initData()
        initViews()
    }

    private fun initData() {
    }

    private fun initViews() {
        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}