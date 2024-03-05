package com.example.articles.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiNetwork {

    private const val API_V2_URL = "https://newsapi.org/v2/"

    fun makeRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_V2_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}