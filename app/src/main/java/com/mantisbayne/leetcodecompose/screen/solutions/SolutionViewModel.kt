package com.mantisbayne.leetcodecompose.screen.solutions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantisbayne.leetcodecompose.data.SolutionModel
import com.mantisbayne.leetcodecompose.data.SolutionsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionViewModel @Inject constructor(private val solutionsService: SolutionsService) :
    ViewModel() {

    private val _solutions = MutableStateFlow(UiState())
    val solutions: StateFlow<UiState> = _solutions.asStateFlow()

    init {
        loadSolutionsFromAssets()
    }

    private fun loadSolutionsFromAssets() {
        viewModelScope.launch {
            try {
                _solutions.update {
                    it.copy(loading = true)
                }

                val loadedSolutions = solutionsService.getSolutions()

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
