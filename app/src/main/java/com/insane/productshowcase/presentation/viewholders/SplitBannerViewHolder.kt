package com.insane.productshowcase.presentation.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.insane.productshowcase.databinding.SplitBannerLayoutBinding
import com.insane.productshowcase.presentation.utils.SplitBannerUI


class SplitBannerViewHolder(private val binding: SplitBannerLayoutBinding) :
    BaseViewHolder<List<SplitBannerUI>>(binding) {
    override fun bind(item: List<SplitBannerUI>): Unit = with(binding) {
        if (item.size > 1) {
            Glide.with(context)
                .load(item[0].image)
                .circleCrop()
                .into(ivBanner1)

            Glide.with(context)
                .load(item[1].image)
                .circleCrop()
                .into(ivBanner2)
        } else {
            ivBanner1.visibility = View.GONE
            ivBanner2.visibility = View.GONE
        }
    }
}