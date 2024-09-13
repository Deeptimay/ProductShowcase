package com.insane.productshowcase.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insane.productshowcase.databinding.HorizontalFreeScrollSingleRowBinding
import com.insane.productshowcase.presentation.utils.ItemUI
import com.insane.productshowcase.presentation.viewholders.HorizontalProductListViewHolder

class HorizontalFreeScrollAdapter(private val horizontalProductList: List<ItemUI>) :
    RecyclerView.Adapter<HorizontalProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HorizontalFreeScrollSingleRowBinding.inflate(inflater, parent, false)
        return HorizontalProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalProductListViewHolder, position: Int) {
        holder.bind(horizontalProductList[position])
    }

    override fun getItemCount(): Int = horizontalProductList.size
}