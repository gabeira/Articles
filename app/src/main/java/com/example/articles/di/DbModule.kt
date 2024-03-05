package com.example.articles.di

import android.content.Context
import com.example.articles.data.database.AppDatabase
import com.example.articles.data.database.ArticleDao
import com.example.articles.data.database.SourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideHeadlineDao(db: AppDatabase): ArticleDao {
        return db.articleDao()
    }

    @Singleton
    @Provides
    fun provideSourcesDao(db: AppDatabase): SourcesDao {
        return db.sourcesDao()
    }
}