package com.insane.productshowcase.data.models

sealed class Response {

    data class Banner(val items: List<Item>) : Response()

    data class HorizontalFreeScroll(val items: List<Item>) : Response()

    data class SplitBanner(val items: List<Item>) : Response()
}

data class Item(
    val image: String, val title: String
)