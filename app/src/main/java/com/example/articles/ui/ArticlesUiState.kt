package com.example.articles.ui

import com.example.articles.data.model.Article

sealed interface ArticlesUiState {

    /**
     * Empty state when the screen is first shown
     */
    data object Initial : ArticlesUiState

    /**
     * Still loading
     */
    data object Loading : ArticlesUiState

    /**
     * Articles has been loaded
     */
    data class Success(val articles: List<Article>) : ArticlesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : ArticlesUiState
}