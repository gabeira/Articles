package com.example.articles.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class RequestSources(
    val status: String,
    val totalResults: Int,
    val sources: List<Source>
)

@Entity
data class Source(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)
