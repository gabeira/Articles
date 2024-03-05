package com.example.articles

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.articles.data.database.AppDatabase
import com.example.articles.data.database.ArticleDao
import com.example.articles.data.model.Article
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticlesDataBaseUnitTest {

    private lateinit var articleDao: ArticleDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        articleDao = db.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val article = Article(
            "www.george.com", "George", "Title of George's Articles",
            "", "", "", ""
        )
//        GlobalScope.launch(Dispatchers.IO) {
//            articleDao.insert(article)
//            articleDao.findByUrl("www.com").collect { byUrl ->
//                assertThat(byUrl, equalTo(article))
//            }
//        }
        //TODO Implement Test properly
    }
}