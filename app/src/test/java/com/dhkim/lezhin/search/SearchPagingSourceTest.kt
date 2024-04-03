package com.dhkim.lezhin.search

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.dhkim.lezhin.data.mapper.toImage
import com.dhkim.lezhin.data.search.datasource.remote.ImageDto
import com.dhkim.lezhin.data.search.datasource.remote.SearchPagingSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchPagingSourceTest {

    private lateinit var fakeSearchApi: FakeSearchApi
    private val images = (1..30).map {
        ImageDto(
            collection = "collection $it",
            thumbnailUrl = "https://test_image$it.png",
            imageUrl = "https://test_image$it.png",
            width = 100,
            height = 100,
            displaySiteName = "site $it",
            docUrl = "https://doc$it.doc",
            dateTime = "2024-04-02"
        )
    }

    @Before
    fun setup() {
        fakeSearchApi = FakeSearchApi().apply {
            images.forEach { addImage(it) }
        }
    }

    @Test
    fun `paging 테스트`() = runTest {
        val pagingSource = SearchPagingSource(
            fakeSearchApi,
            "대한민국"
        )

        val pager = TestPager(
            config = PagingConfig(pageSize = 30),
            pagingSource = pagingSource
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(result.data, images.map { it.toImage() })
    }

    @Test
    fun `에러 테스트`() = runTest {
        val pagingSource = SearchPagingSource(
            fakeSearchApi,
            "대한민국"
        )

        fakeSearchApi.setReturnsError()

        val pager = TestPager(
            config = PagingConfig(pageSize = 30),
            pagingSource = pagingSource
        )

        val result = pager.refresh()
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}