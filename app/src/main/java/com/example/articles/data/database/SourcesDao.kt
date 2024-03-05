package com.example.articles.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.articles.data.model.Source
import kotlinx.coroutines.flow.Flow


@Dao
interface SourcesDao {

    @Query("SELECT * FROM source")
    fun getAllSelectedSources(): Flow<List<Source>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg source: Source)

    @Delete
    fun delete(source: Source)
}