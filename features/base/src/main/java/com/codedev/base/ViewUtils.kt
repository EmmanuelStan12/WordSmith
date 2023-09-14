package com.codedev.base

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun View.toggleVisibility(value: Boolean? = null) {
    if (value == null) {
        this.isVisible = !this.isVisible
        return
    }
    this.isVisible = value
}

inline fun Fragment.launchInLifecycle(state: Lifecycle.State = Lifecycle.State.STARTED, crossinline launch: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(state) {
            launch()
        }
    }
}