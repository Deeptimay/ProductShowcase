package com.insane.productshowcase.presentation.utils

import com.insane.productshowcase.data.models.Item
import com.insane.productshowcase.data.models.Response
import com.squareup.moshi.Json

fun Response.Banner.toBannerUI() = BannerUI("banner", items = items.map { it.toItemUI() })

fun Response.HorizontalFreeScroll.toHorizontalFreeScrollUI() = HorizontalFreeScrollUI("horizontalFreeScroll", items = items.map { it.toItemUI() })

fun Item.toItemUI() = ItemUI(image, title)

fun Response.SplitBanner.toSplitBannerUI() = SplitBannerUI("splitBanner", items = items.map { it.toItemUI() })


fun Response.toBaseUI(): BaseUI =
    when (this) {
        is Response.Banner -> this.toBannerUI()
        is Response.HorizontalFreeScroll -> this.toHorizontalFreeScrollUI()
        is Response.SplitBanner -> this.toSplitBannerUI()
    }

fun List<Response>.toBaseUIList(): List<BaseUI> {
    return this.map { it.toBaseUI() }
}

enum class ResponseType {
    @Json(name = "banner")
    BANNER,

    @Json(name = "horizontalFreeScroll")
    HORIZONTAL_FREE_SCROLL,

    @Json(name = "splitBanner")
    SPLIT_BANNER
}