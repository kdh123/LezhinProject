package com.dhkim.lezhin.data.search.datasource.remote

import com.dhkim.lezhin.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {

    @GET("image")
    suspend fun searchImage(
        @Header("Authorization") token: String = BuildConfig.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchResultDto>
}