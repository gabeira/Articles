package com.example.articles.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class RequestArticles(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Entity
data class Article(
    @PrimaryKey var url: String,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "url_to_image") var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
//    var source: Source
)