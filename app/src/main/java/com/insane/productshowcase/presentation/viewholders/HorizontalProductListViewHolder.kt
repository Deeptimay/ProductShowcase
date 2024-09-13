package com.insane.productshowcase.presentation.viewholders

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.insane.productshowcase.databinding.HorizontalFreeScrollSingleRowBinding
import com.insane.productshowcase.presentation.utils.ItemUI


class HorizontalProductListViewHolder(private val binding: HorizontalFreeScrollSingleRowBinding) : BaseViewHolder<ItemUI>(binding) {
    override fun bind(item: ItemUI) = with(binding) {
        Glide.with(context)
            .load(item.image)
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .into(ivProduct)

        tvProductName.text = item.title
    }
}