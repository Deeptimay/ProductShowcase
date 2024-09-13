package com.insane.productshowcase.presentation.viewholders

import com.bumptech.glide.Glide
import com.insane.productshowcase.databinding.BannerLayoutBinding
import com.insane.productshowcase.presentation.utils.BannerUI


class BannerViewHolder(private val binding: BannerLayoutBinding) :
    BaseViewHolder<BannerUI>(binding) {

    override fun bind(item: BannerUI): Unit = with(binding) {
        Glide.with(context)
            .load(item.image)
            .circleCrop()
            .into(ivBanner)
    }
}