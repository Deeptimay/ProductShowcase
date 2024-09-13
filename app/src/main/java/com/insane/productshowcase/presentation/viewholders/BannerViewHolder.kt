package com.insane.productshowcase.presentation.viewholders

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.insane.productshowcase.databinding.BannerLayoutBinding
import com.insane.productshowcase.presentation.utils.BannerUI


class BannerViewHolder(private val binding: BannerLayoutBinding) :
    BaseViewHolder<BannerUI>(binding) {

    override fun bind(item: BannerUI): Unit = with(binding) {
        Glide.with(context)
            .load(item.items[0].image)
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .into(ivBanner)

        tvProductName.text = item.items[0].title
    }
}