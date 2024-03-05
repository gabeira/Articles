package com.example.articles.ui.headlines

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articles.data.ArticlesRepository
import com.example.articles.data.model.Article
import com.example.articles.ui.ArticlesUiState
import com.example.articles.ui.components.Arguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ArticlesUiState> =
        MutableStateFlow(ArticlesUiState.Initial)
    val uiState: StateFlow<ArticlesUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = ArticlesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            articlesRepository.loadArticlesFromServer({ articles ->
                _uiState.value = ArticlesUiState.Success(articles)
            }, { error ->
                _uiState.value = ArticlesUiState.Error(error)
            })
        }
    }

    @Suppress("unused")
    val alreadySaved: Boolean = savedStateHandle.get<Boolean>(Arguments.SAVED_KEY) ?: false
    val title: String = savedStateHandle.get<String>(Arguments.TITLE_KEY) ?: ""
    val isSaved = articlesRepository.isSaved(title)

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articlesRepository.insert(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articlesRepository.delete(article)
        }
    }
}