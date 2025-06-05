package com.mantisbayne.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mantisbayne.leetcodecompose.screen.solutions.SolutionScreen
import com.mantisbayne.leetcodecompose.screen.algorithm.VisualizerScreen
import com.mantisbayne.leetcodecompose.screen.solutions.SolutionViewModel
import com.mantisbayne.leetcodecompose.screen.algorithm.VisualizerViewModel
import com.mantisbayne.leetcodecompose.screen.matrix_builder.MatrixScreen

enum class NavHostRoutes {
    HOME,
    MATRIX_BUILDER,
    VISUALIZER
}

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = NavHostRoutes.MATRIX_BUILDER.name,
        modifier = modifier
    ) {
        composable(route = NavHostRoutes.MATRIX_BUILDER.name) {
            MatrixScreen(
                navController = navHostController
            )
        }
        composable(route = NavHostRoutes.HOME.name) {
            SolutionScreen(
                viewModel = hiltViewModel<SolutionViewModel>(),
                navController = navHostController
            )
        }
        composable(route = NavHostRoutes.HOME.name) {
            SolutionScreen(
                viewModel = hiltViewModel<SolutionViewModel>(),
                navController = navHostController
            )
        }

        composable(route = NavHostRoutes.VISUALIZER.name) {
            VisualizerScreen(
                viewModel = hiltViewModel<VisualizerViewModel>()
            )
        }
    }
}
