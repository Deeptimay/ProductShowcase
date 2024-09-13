package com.insane.productshowcase.presentation.viewholders

import androidx.recyclerview.widget.LinearLayoutManager
import com.insane.productshowcase.databinding.HorizontalFreeScrollLayoutBinding
import com.insane.productshowcase.presentation.adapters.HorizontalFreeScrollAdapter
import com.insane.productshowcase.presentation.utils.HorizontalFreeScrollUI


class HorizontalFreeScrollViewHolder(private val binding: HorizontalFreeScrollLayoutBinding) :
    BaseViewHolder<List<HorizontalFreeScrollUI>>(binding) {
    override fun bind(item: List<HorizontalFreeScrollUI>): Unit = with(binding) {
        rvHorizontalScroll.apply {
            adapter = HorizontalFreeScrollAdapter(item)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}