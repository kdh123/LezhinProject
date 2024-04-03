package com.dhkim.lezhin.domain.search

import androidx.paging.PagingData
import com.dhkim.lezhin.domain.search.model.Image
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchImages(query: String): Flow<PagingData<Image>>
}