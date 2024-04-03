package com.dhkim.lezhin.data.search.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dhkim.lezhin.domain.search.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val api: SearchApi
) {

    fun searchImage(query: String): Flow<PagingData<Image>> {
        return Pager(
            PagingConfig(pageSize = 30)
        ) {
            SearchPagingSource(api, query)
        }.flow
    }
}