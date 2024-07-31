package net.thebookofcode.shoppinglistitemswithtdd.data.remote.responses

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)
