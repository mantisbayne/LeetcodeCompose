package com.mantisbayne.leetcodecompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantisbayne.leetcodecompose.App
import com.mantisbayne.leetcodecompose.data.SolutionModel
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SolutionViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _solutions = MutableStateFlow(UiState())
    val solutions: StateFlow<UiState> = _solutions.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    init {
        loadSolutionsFromAssets()
    }

    private fun loadSolutionsFromAssets() {
        viewModelScope.launch {
            try {
                _solutions.update {
                    it.copy(loading = true)
                }
                val inputStream = application.assets.open("solutions.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val loadedSolutions = json.decodeFromString<List<SolutionModel>>(jsonString)
                _solutions.update {
                    if (loadedSolutions.isEmpty()) {
                        it.copy(
                            loading = false,
                            empty = true
                        )
                    } else {
                        it.copy(
                            loading = false,
                            solutions = loadedSolutions
                        )
                    }
                }
            } catch (e: Exception) {
                _solutions.update {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val error: String? = null,
    val empty: Boolean = false,
    val solutions: List<SolutionModel> = emptyList()
)
