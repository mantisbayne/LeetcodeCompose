package com.mantisbayne.leetcodecompose.screen.matrix_builder

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MatrixScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<MatrixViewModel>()

    // states
    MatrixScreenContent()
}

@Composable
fun MatrixScreenContent() {
    Text("Testing")
}
