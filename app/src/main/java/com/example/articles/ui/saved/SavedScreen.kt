package com.example.articles.ui.saved

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.articles.R
import com.example.articles.ui.ArticlesUiState
import com.example.articles.ui.components.ArticleCompose
import com.example.articles.ui.components.Screen
import com.google.gson.Gson

@Composable
fun SavedScreen(navController: NavHostController) {
    val viewModel: SavedViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(initial = ArticlesUiState.Initial)
    when (uiState) {
        ArticlesUiState.Initial -> {
            //Nothing to show
        }

        ArticlesUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)
            ) {
                CircularProgressIndicator()
            }
        }

        is ArticlesUiState.Success -> {
            LazyColumn(modifier = Modifier.padding(bottom = 58.dp)) {
                itemsIndexed((uiState as ArticlesUiState.Success).articles) { index, article ->
                    ArticleCompose(article) {
                        val jsonArgs = Uri.encode(Gson().toJson(article))
                        val route = Screen.DetailScreen.route + "/true/${article.title}/$jsonArgs"
                        navController.navigate(route)
                    }
                }
            }
        }

        is ArticlesUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)) {
                Text(stringResource(id = R.string.no_articles_saved), textAlign = TextAlign.Center)
            }
        }
    }
}