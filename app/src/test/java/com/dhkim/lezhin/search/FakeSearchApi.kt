package com.dhkim.lezhin.search

import com.dhkim.lezhin.data.search.datasource.remote.ImageDto
import com.dhkim.lezhin.data.search.datasource.remote.MetaData
import com.dhkim.lezhin.data.search.datasource.remote.SearchApi
import com.dhkim.lezhin.data.search.datasource.remote.SearchResultDto
import retrofit2.Response

class FakeSearchApi : SearchApi {

    private val result = mutableListOf<ImageDto>()
    private var isError = false

    override suspend fun searchImage(token: String, query: String, page: Int, size: Int): Response<SearchResultDto> {
        val metaData = MetaData(
            totalCount = 100,
            pageableCount = 10,
            isEnd = false
        )
        val searchResultDto = SearchResultDto(metaData, result)

        return if (isError) {
            throw Exception()
        } else {
            Response.success(searchResultDto)
        }
    }

    fun setReturnsError() {
        isError = true
    }

    fun addImage(image: ImageDto) {
        result.add(image)
    }
}