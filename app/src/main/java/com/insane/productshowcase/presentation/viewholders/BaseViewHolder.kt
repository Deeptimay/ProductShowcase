package com.insane.productshowcase.presentation.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<in T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    val radius = 20
    val context: Context = binding.root.context
    abstract fun bind(item: T)
}