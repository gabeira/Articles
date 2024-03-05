package com.example.articles.ui.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articles.data.ArticlesRepository
import com.example.articles.data.model.Article
import com.example.articles.ui.ArticlesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
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

    private val _articleByUrlSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val articleByUrlSaved: StateFlow<Boolean> = _articleByUrlSaved.asStateFlow()

    fun findArticleByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            articlesRepository.findByUrl(url) { art ->
                _articleByUrlSaved.value = art != null
            }
        }
    }

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