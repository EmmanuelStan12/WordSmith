package com.codedev.base

import android.view.View
import androidx.core.view.isVisible

fun View.toggleVisibility(value: Boolean? = null) {
    if (value == null) {
        this.isVisible = !this.isVisible
        return
    }
    this.isVisible = value
}