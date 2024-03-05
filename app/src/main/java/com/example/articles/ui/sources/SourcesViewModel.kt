package com.example.articles.ui.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articles.data.SourcesRepository
import com.example.articles.data.model.Source
import com.example.articles.ui.SourcesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val repository: SourcesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SourcesUiState> =
        MutableStateFlow(SourcesUiState.Initial)
    val uiState: StateFlow<SourcesUiState> = _uiState.asStateFlow()

    val listOfSelected = repository.allSelectedSourcesFromDataBase

    init {
        _uiState.value = SourcesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadSourcesFromServer({
                _uiState.value = SourcesUiState.Success(it)
            }, { error ->
                _uiState.value = SourcesUiState.Error(error)
            })
        }
    }

    fun updateSavedSources(checked: Boolean, source: Source) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checked) {
                repository.insert(source)
            } else {
                repository.delete(source)
            }
        }
    }
}