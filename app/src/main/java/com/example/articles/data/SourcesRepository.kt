package com.example.articles.data

import androidx.annotation.WorkerThread
import com.example.articles.data.database.SourcesDao
import com.example.articles.data.model.Source
import com.example.articles.data.network.NewsApiNetwork
import com.example.articles.data.network.NewsApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SourcesRepository @Inject constructor(private val sourcesDao: SourcesDao) {

    //TODO save key into a secure place
    private val apiKey = "685ed76ace834dd586dc0bb8635b610e"

    private val retrofitService: NewsApiService =
        NewsApiNetwork.makeRetrofitService().create(NewsApiService::class.java)

    suspend fun loadSourcesFromServer(
        onSuccess: (List<Source>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val request = retrofitService.requestSourcesResponse(apiKey)
            if (request.isSuccessful) {
                allSelectedSourcesFromDataBase.collect {


                    val complete = request.body()
                    complete?.let { onSuccess(it.sources) }
                }
            } else {
                onError("Could not load ${request.errorBody()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allSelectedSourcesFromDataBase: Flow<List<Source>> = sourcesDao.getAllSelectedSources()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(source: Source) = sourcesDao.insert(source)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(source: Source) = sourcesDao.delete(source)

}
