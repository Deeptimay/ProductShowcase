package com.insane.productshowcase.presentation.viewholders

import com.bumptech.glide.Glide
import com.insane.productshowcase.databinding.HorizontalFreeScrollSingleRowBinding
import com.insane.productshowcase.presentation.utils.HorizontalFreeScrollUI


class HorizontalProductListViewHolder(private val binding: HorizontalFreeScrollSingleRowBinding) :
    BaseViewHolder<HorizontalFreeScrollUI>(binding) {
    override fun bind(item: HorizontalFreeScrollUI) = with(binding) {
        Glide.with(context)
            .load(item.image)
            .circleCrop()
            .into(ivProduct)

        tvProductName.text = item.title
    }
}