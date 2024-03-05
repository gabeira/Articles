package com.example.articles.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.articles.data.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT EXISTS(SELECT 1 FROM article WHERE title = :title LIMIT 1)")
    fun isSaved(title: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg headline: Article)

    @Delete
    fun delete(headline: Article)
}