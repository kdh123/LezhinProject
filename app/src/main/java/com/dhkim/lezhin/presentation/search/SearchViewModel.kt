package com.dhkim.lezhin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.search.SearchRepository
import com.dhkim.lezhin.domain.search.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _queryFlow: MutableStateFlow<String> = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()

    private val _resultFlow = MutableStateFlow<PagingData<Image>>(PagingData.empty())
    val resultFlow = _resultFlow.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchFlow = _queryFlow.debounce(1000L).distinctUntilChanged()
        .flatMapLatest { query ->
            searchRepository.searchImages(query)
        }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.getBookmarks().combine(searchFlow) { bookmarks, result ->
                //thumnbnail을 id로 저장
                val ids = bookmarks.map { it.thumbnailUrl }

                result.map {
                    it.copy(isBookmark = ids.contains(it.thumbnailUrl))
                }
            }.catch {
                val loadStates = LoadStates(
                    refresh = LoadState.Error(it),
                    prepend = LoadState.Error(it),
                    append = LoadState.Error(it),
                )

                _resultFlow.value = PagingData.empty(sourceLoadStates = loadStates)
            }.collect {
                _resultFlow.value = it
            }
        }
    }

    fun query(query: String) {
        _queryFlow.value = query
    }
}