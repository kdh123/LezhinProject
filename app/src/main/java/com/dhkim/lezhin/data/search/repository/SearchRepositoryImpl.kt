package com.dhkim.lezhin.data.search.repository

import androidx.paging.PagingData
import com.dhkim.lezhin.data.search.datasource.remote.SearchRemoteDataSource
import com.dhkim.lezhin.domain.search.SearchRepository
import com.dhkim.lezhin.domain.search.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
) : SearchRepository {

    override fun searchImages(query: String): Flow<PagingData<Image>> {
        return remoteDataSource.searchImage(query)
    }
}