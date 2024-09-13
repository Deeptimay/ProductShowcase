package com.insane.productshowcase.presentation.utils


interface BaseUI

data class BannerUI(
    val sectionType: String,
    val items: List<ItemUI>
) : BaseUI

data class HorizontalFreeScrollUI(
    val sectionType: String,
    val items: List<ItemUI>
) : BaseUI

data class SplitBannerUI(
    val sectionType: String,
    val items: List<ItemUI>
) : BaseUI

data class ItemUI(
    val image: String,
    val title: String
)

