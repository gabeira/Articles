package com.example.articles.data

import androidx.annotation.WorkerThread
import com.example.articles.data.database.ArticleDao
import com.example.articles.data.database.SourcesDao
import com.example.articles.data.model.Article
import com.example.articles.data.network.NewsApiNetwork
import com.example.articles.data.network.NewsApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val sourcesDao: SourcesDao
) {

    //TODO save key into a secure place
    private val apiKey = "685ed76ace834dd586dc0bb8635b610e"

    private val service: NewsApiService =
        NewsApiNetwork.makeRetrofitService().create(NewsApiService::class.java)

    suspend fun loadArticlesFromServer(
        onSuccess: (List<Article>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            sourcesDao.getAllSelectedSources().collect { savedSources ->
                val sources = savedSources.joinToString(",") { it.id }
                val language = "en"
                val request = service.requestArticlesFromSourceResponse(sources, language, apiKey)

                if (request.isSuccessful) {
                    val complete = request.body()
                    complete?.let { onSuccess(it.articles) }
                } else {
                    onError("Could not load ${request.errorBody()?.string()}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allArticlesFromDataBase: Flow<List<Article>> = articleDao.getAll()

    fun isSaved(title: String) = articleDao.isSaved(title)

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(headline: Article) = articleDao.insert(headline)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(headline: Article) = articleDao.delete(headline)

}