package com.dhkim.lezhin.di

import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.search.SearchRepository
import com.dhkim.lezhin.data.bookmark.repository.BookmarkRepositoryImpl
import com.dhkim.lezhin.data.search.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository
}