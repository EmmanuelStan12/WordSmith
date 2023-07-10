package com.codedev.base.custom_views

import com.codedev.base.R
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.codedev.base.databinding.LayoutFeatureItemBinding


class LayoutFeatureView @JvmOverloads
constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr:Int = 0
): FrameLayout(context,attrs,defStyleAttr) {

    private val binding: LayoutFeatureItemBinding

    private var onClick: (() -> Unit)? = null

    init {
        binding = LayoutFeatureItemBinding.inflate(LayoutInflater.from(context), this)
        //initialize()
    }

    fun setOnClickListener(onClick: () -> Unit) {
        this.onClick = onClick
    }

    /*private fun initialize() {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LayoutFeatureView,
            defStyleAttr,
            R.style.Theme_Dictionary
        )

        val icon = typedArray.getInteger(R.styleable.LayoutFeatureView_icon, R.drawable.ic_translate)
        binding.ivCardImage.setImageResource(icon)
        val text = typedArray.getString(R.styleable.LayoutFeatureView_text)
        binding.tvHeader.text = text

        binding.root.setOnClickListener {
            onClick?.invoke()
        }

        typedArray.recycle()
    }*/
}