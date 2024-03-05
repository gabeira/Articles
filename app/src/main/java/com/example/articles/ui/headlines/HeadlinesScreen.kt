package com.example.articles.ui.headlines

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.articles.ui.ArticlesUiState
import com.example.articles.ui.components.ArticleCompose
import com.example.articles.ui.components.Screen
import com.google.gson.Gson

@Composable
fun HeadlinesScreen(navController: NavHostController) {
    val viewModel: HeadlinesViewModel = hiltViewModel()
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
            val list = (uiState as ArticlesUiState.Success).articles
            LazyColumn(modifier = Modifier.padding(bottom = 58.dp)) {
                itemsIndexed(list) { index, article ->
                    ArticleCompose(article) {
                        val jsonArgs = Uri.encode(Gson().toJson(article))
                        navController.navigate(route = Screen.DetailScreen.route + "/$jsonArgs")
                    }
                }
            }
        }

        is ArticlesUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)) {
                Text((uiState as ArticlesUiState.Error).errorMessage, textAlign = TextAlign.Center)
            }
        }
    }
}