package com.mantisbayne.leetcodecompose.screen.matrix_builder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatrixViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MatrixUiState())
    val state = _state.asStateFlow()


}

data class MatrixUiState(
    val isLoading: Boolean = false,
    val isLoadingMatrix: Boolean = false,
    val rows: Int = 0,
    val columns: Int = 0,
    val animationSteps: List<MatrixAnimationStep> = emptyList(),
    val errorMessage: String = "",
    val menuItems: List<String> = emptyList(),
    val currentSelectedProblem: ProblemItem = ProblemItem(),
    val problemDescriptionVisibility: Boolean = false
)

data class MatrixAnimationStep(
    val row: Int,
    val col: Int,
    val description: String
)

data class ProblemItem(
    val id: Int = 0,
    val name: String = "",
    val description: String = ""
)

sealed interface MatrixEvent {
    data class Error(val message: String) : MatrixEvent
    data class SelectProblem(val problem: ProblemItem) : MatrixEvent
    data class BuildMatrix(val rows: Int, val cols: Int, val problemId: String): MatrixEvent
}
