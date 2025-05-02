package com.mantisbayne.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mantisbayne.leetcodecompose.screen.SolutionScreen
import com.mantisbayne.leetcodecompose.screen.VisualizerScreen
import com.mantisbayne.leetcodecompose.viewmodel.SolutionViewModel
import com.mantisbayne.leetcodecompose.viewmodel.VisualizerViewModel

enum class NavHostRoutes {
    HOME,
    VISUALIZER
}

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = NavHostRoutes.HOME.name,
        modifier = modifier
    ) {
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
