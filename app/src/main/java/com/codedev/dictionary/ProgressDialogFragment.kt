package com.codedev.dictionary

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.codedev.dictionary.databinding.DialogProgressBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProgressDialogFragment: DialogFragment() {

    private lateinit var binding: DialogProgressBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), theme)
            .also {
                val dialogView = onCreateView(layoutInflater, null, savedInstanceState)
                dialogView?.let { it2 -> onViewCreated(it2, savedInstanceState) }
                it.setView(dialogView)
            }
            .create()
            .also{ it.setCanceledOnTouchOutside(false) }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogProgressBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){

    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}