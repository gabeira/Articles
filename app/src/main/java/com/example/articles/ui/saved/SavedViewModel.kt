package com.example.articles.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articles.data.ArticlesRepository
import com.example.articles.ui.ArticlesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ArticlesUiState> =
        MutableStateFlow(ArticlesUiState.Initial)
    val uiState: StateFlow<ArticlesUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = ArticlesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            articlesRepository.allArticlesFromDataBase.collect() { articles ->
                if (articles.isEmpty()) _uiState.value = ArticlesUiState.Error("")
                else _uiState.value = ArticlesUiState.Success(articles)
            }
        }
    }
}