package com.example.articles.ui

import com.example.articles.data.model.Source

sealed interface SourcesUiState {

    /**
     * Empty state when the screen is first shown
     */
    data object Initial : SourcesUiState

    /**
     * Still loading
     */
    data object Loading : SourcesUiState

    /**
     * Data has been generated
     */
    data class Success(val sources: List<Source>) : SourcesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : SourcesUiState
}