package com.dhkim.lezhin.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _bookmarkFlow: MutableStateFlow<List<Bookmark>> = MutableStateFlow(listOf())
    val bookmarkFlow = _bookmarkFlow.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.getBookmarks()
                .catch {
                }
                .collect {
                    _bookmarkFlow.value = it
                }
        }
    }

    fun saveBookmark(id: String, thumbnailUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.saveBookmark(Bookmark(id, thumbnailUrl))
        }
    }

    fun deleteBookmark(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.deleteBookmark(id)
        }
    }

    fun deleteAllBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.deleteAllBookmark()
        }
    }
}