package com.example.articles.ui.sources

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
import com.example.articles.ui.SourcesUiState
import com.example.articles.ui.components.SourceCompose

@Composable
fun SourcesScreen() {
    val viewModel: SourcesViewModel = hiltViewModel()
    val listOfSelected by viewModel.listOfSelected.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState.collectAsState(initial = SourcesUiState.Initial)
    when (uiState) {
        SourcesUiState.Initial -> {
            //Nothing to show
        }

        SourcesUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)
            ) {
                CircularProgressIndicator()
            }
        }

        is SourcesUiState.Success -> {
            LazyColumn(modifier = Modifier.padding(bottom = 58.dp)) {
                itemsIndexed((uiState as SourcesUiState.Success).sources) { index, source ->
                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier.padding(8.dp, 0.dp),
                            color = Color.LightGray,
                            thickness = 0.6.dp,
                        )
                    }
                    SourceCompose(source, listOfSelected.contains(source)) { checked ->
                        viewModel.updateSavedSources(checked, source)
                    }
                }
            }
        }

        is SourcesUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)) {
                Text((uiState as SourcesUiState.Error).errorMessage, textAlign = TextAlign.Center)
            }
        }
    }
}