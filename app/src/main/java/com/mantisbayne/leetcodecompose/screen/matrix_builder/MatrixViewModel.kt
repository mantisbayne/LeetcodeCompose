package com.mantisbayne.leetcodecompose.screen.matrix_builder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatrixViewModel @Inject constructor() : ViewModel() {


}

data class MatrixUiState(
    val isLoading: Boolean = false,
    val isLoadingMatrix: Boolean = false,

    val menuItem: List<String> = emptyList(),
    val solution: String = ""
)

sealed interface MatrixEvent {
    data class Error(val message: String) : MatrixEvent

}
