package com.example.articles.data.network

import com.example.articles.data.model.RequestArticles
import com.example.articles.data.model.RequestSources
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun requestAllArticlesResponse(
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Response<RequestArticles>

    @GET("top-headlines")
    suspend fun requestArticlesFromSourceResponse(
        @Query("sources") from: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Response<RequestArticles>

    @GET("top-headlines/sources")
    suspend fun requestSourcesResponse(@Query("apiKey") apiKey: String): Response<RequestSources>

}