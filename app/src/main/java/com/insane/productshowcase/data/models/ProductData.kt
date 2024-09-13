package com.insane.productshowcase.data.models

import com.squareup.moshi.Json


class ApiResponse : ArrayList<ResponseItem>()

data class ResponseItem(
    val items: List<Item>,
    val sectionType: String
)

data class Item(
    val image: String,
    val title: String
)


enum class ResponseType {
    @Json(name = "banner")
    BANNER,

    @Json(name = "horizontalFreeScroll")
    HORIZONTAL_FREE_SCROLL,

    @Json(name = "splitBanner")
    SPLIT_BANNER
}