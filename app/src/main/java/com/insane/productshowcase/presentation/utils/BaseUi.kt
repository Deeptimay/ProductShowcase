package com.insane.productshowcase.presentation.utils


interface BaseUI

data class BannerUI(
    val image: String,
    val title: String
) : BaseUI

data class HorizontalFreeScrollUI(
    val image: String,
    val title: String
) : BaseUI

data class SplitBannerUI(
    val image: String,
    val title: String
) : BaseUI


