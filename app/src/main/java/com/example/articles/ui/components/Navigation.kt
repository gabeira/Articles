package com.example.articles.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.articles.data.model.Article
import com.example.articles.ui.headlines.HeadlinesScreen
import com.example.articles.ui.saved.SavedScreen
import com.google.gson.Gson

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.HeadlineScreen.route) {
            HeadlinesScreen(navController)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen(navController)
        }
        composable(
            route = Screen.DetailScreen.route +
                    "/{${Arguments.SAVED_KEY}}/{${Arguments.TITLE_KEY}}/{${Arguments.ARTICLE_KEY}}",
            arguments = listOf(
                navArgument(Arguments.SAVED_KEY) {
                    type = NavType.BoolType
                    nullable = false
                },
                navArgument(Arguments.TITLE_KEY) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(Arguments.ARTICLE_KEY) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val alreadySaved = entry.arguments?.getBoolean(Arguments.SAVED_KEY, false) ?: false
            val argument = entry.arguments?.getString(Arguments.ARTICLE_KEY)
            val article = Gson().fromJson(argument, Article::class.java)
            ArticleDetailScreen(navController, article, alreadySaved)
        }
    }
}

object Arguments {
    const val TITLE_KEY = "title"
    const val SAVED_KEY = "saved"
    const val ARTICLE_KEY = "article"
}

sealed class Screen(val route: String) {
    object HeadlineScreen : Screen("headline_screen")
    object SavedScreen : Screen("saved_screen")
    object DetailScreen : Screen("detail_screen")
}