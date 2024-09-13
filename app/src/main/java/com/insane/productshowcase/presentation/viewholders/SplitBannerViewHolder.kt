package com.insane.productshowcase.presentation.viewholders

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.insane.productshowcase.databinding.SplitBannerLayoutBinding
import com.insane.productshowcase.presentation.utils.SplitBannerUI


class SplitBannerViewHolder(private val binding: SplitBannerLayoutBinding) :
    BaseViewHolder<SplitBannerUI>(binding) {
    override fun bind(item: SplitBannerUI): Unit = with(binding) {
        if (item.items.size > 1) {
            Glide.with(context)
                .load(item.items[0].image)
                .apply(RequestOptions().transform(RoundedCorners(radius)))
                .into(ivBanner1)

            Glide.with(context)
                .load(item.items[1].image)
                .apply(RequestOptions().transform(RoundedCorners(radius)))
                .into(ivBanner2)

            tvProductName1.text = item.items[0].title
            tvProductName2.text = item.items[1].title
        }
    }
}