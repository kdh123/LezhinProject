package com.dhkim.lezhin.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import com.dhkim.lezhin.domain.search.SearchRepository
import com.dhkim.lezhin.domain.search.model.Image
import kotlinx.coroutines.flow.Flow

class FakeSearchRepository : SearchRepository {

    private val items: MutableList<Image> = mutableListOf()
    private val pagingSourceFactory = items.asPagingSourceFactory()
    private val pagingSource = pagingSourceFactory()

    override fun searchImages(query: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(pageSize = 30)
        ) {
            pagingSource
        }.flow
    }

    fun addItems(images: List<Image>) {
        images.forEach {
            items.add(it)
        }
    }
}