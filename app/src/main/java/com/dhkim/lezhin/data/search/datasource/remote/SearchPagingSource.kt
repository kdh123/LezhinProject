package com.dhkim.lezhin.data.search.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dhkim.lezhin.data.mapper.toImage
import com.dhkim.lezhin.domain.search.model.Image
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    val api: SearchApi,
    val query: String
) : PagingSource<Int, Image>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Image> {
        try {
            val nextPageNumber = params.key ?: 1
            val result = api.searchImage(
                query = query,
                page = nextPageNumber,
                size = 30
            )

            var images: List<Image> = listOf()

            if (result.isSuccessful) {
                result.body()?.run {
                    images = documents.map { it.toImage() }
                }
            } else {
                images = listOf()
            }

            return LoadResult.Page(
                data = images,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = if (query.isNotEmpty()) {
                    nextPageNumber + 1
                } else null
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}