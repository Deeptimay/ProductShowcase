package com.insane.productshowcase.data.models

import com.squareup.moshi.Json

sealed class Response {

    data class Banner(val items: List<Item>) : Response()

    data class HorizontalFreeScroll(val items: List<Item>) : Response()

    data class SplitBanner(val items: List<Item>) : Response()
}

data class Item(
    val image: String, val title: String
)


enum class ResponseType {
    @Json(name = "banner")
    BANNER,

    @Json(name = "horizontalFreeScroll")
    HORIZONTAL_FREE_SCROLL,

    @Json(name = "splitBanner")
    SPLIT_BANNER
}