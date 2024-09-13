package com.insane.productshowcase.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insane.productshowcase.data.models.ResponseType
import com.insane.productshowcase.databinding.BannerLayoutBinding
import com.insane.productshowcase.databinding.HorizontalFreeScrollLayoutBinding
import com.insane.productshowcase.databinding.SplitBannerLayoutBinding
import com.insane.productshowcase.presentation.utils.BannerUI
import com.insane.productshowcase.presentation.utils.BaseUI
import com.insane.productshowcase.presentation.utils.HorizontalFreeScrollUI
import com.insane.productshowcase.presentation.utils.SplitBannerUI
import com.insane.productshowcase.presentation.viewholders.BannerViewHolder
import com.insane.productshowcase.presentation.viewholders.BaseViewHolder
import com.insane.productshowcase.presentation.viewholders.HorizontalFreeScrollViewHolder
import com.insane.productshowcase.presentation.viewholders.SplitBannerViewHolder


class DynamicListAdapter(private var productData: List<BaseUI>) : RecyclerView.Adapter<BaseViewHolder<BaseUI>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseUI> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ResponseType.BANNER.ordinal -> {
                val binding = BannerLayoutBinding.inflate(inflater, parent, false)
                BannerViewHolder(binding)
            }

            ResponseType.HORIZONTAL_FREE_SCROLL.ordinal -> {
                val binding = HorizontalFreeScrollLayoutBinding.inflate(inflater, parent, false)
                HorizontalFreeScrollViewHolder(binding)
            }

            ResponseType.SPLIT_BANNER.ordinal -> {
                val binding = SplitBannerLayoutBinding.inflate(inflater, parent, false)
                SplitBannerViewHolder(binding)
            }

            else -> throw IllegalArgumentException("The view type value of $viewType is not supported")
        } as BaseViewHolder<BaseUI>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseUI>, position: Int) {
        holder.bind(productData[position])
    }

    override fun getItemCount(): Int = productData.size

    override fun getItemViewType(position: Int): Int {
        return when (productData[position]) {
            is BannerUI -> ResponseType.BANNER.ordinal
            is HorizontalFreeScrollUI -> ResponseType.HORIZONTAL_FREE_SCROLL.ordinal
            is SplitBannerUI -> ResponseType.SPLIT_BANNER.ordinal
            else -> -1
        }
    }
}