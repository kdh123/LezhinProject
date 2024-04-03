package com.dhkim.lezhin.data.search.datasource.remote

data class SearchResultDto(
    val meta: MetaData,
    val documents: List<ImageDto>
)