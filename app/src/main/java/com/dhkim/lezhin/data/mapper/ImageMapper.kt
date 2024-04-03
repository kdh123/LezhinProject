package com.dhkim.lezhin.data.mapper

import com.dhkim.lezhin.data.search.datasource.remote.ImageDto
import com.dhkim.lezhin.domain.search.model.Image

fun ImageDto.toImage() = Image(
    id = thumbnailUrl,
    thumbnailUrl = thumbnailUrl,
    isBookmark = false
)