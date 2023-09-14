package com.codedev.dictionary.ui.about_us

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import com.codedev.base.HTMLParser
import com.codedev.home.R
import com.codedev.home.databinding.FragmentAboutUsBinding
import com.codedev.home.databinding.FragmentExitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private var _binding: FragmentAboutUsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAboutUsBinding.bind(view)

        initData()
        initViews()
    }

    private fun initData() {}

    private fun initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvAboutUs.text =
                Html.fromHtml(HTMLParser.createAboutMe(), Html.FROM_HTML_MODE_COMPACT);
        } else
            binding.tvAboutUs.text = Html.fromHtml(HTMLParser.createAboutMe())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}