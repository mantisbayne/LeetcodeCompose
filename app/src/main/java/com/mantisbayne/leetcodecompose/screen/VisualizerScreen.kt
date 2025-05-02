package com.mantisbayne.leetcodecompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mantisbayne.leetcodecompose.viewmodel.Algorithm
import com.mantisbayne.leetcodecompose.viewmodel.Grid
import com.mantisbayne.leetcodecompose.viewmodel.VisualizerViewModel

@Composable
fun VisualizerScreen(
    modifier: Modifier = Modifier,
    viewModel: VisualizerViewModel
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        var expanded by rememberSaveable { mutableStateOf(false) }
        var showVisualizer by rememberSaveable { mutableStateOf(false) }
        var showDynamicProgramming by rememberSaveable { mutableStateOf(false) }
        var showCoinChange by rememberSaveable { mutableStateOf(false) }
        val grid by viewModel.grid.collectAsState()
        val algorithm by viewModel.algorithm.collectAsState()

        Button(
            onClick = {
                showVisualizer = false
                showCoinChange = false
                showDynamicProgramming = false
                expanded = true
            }
        ) {
            Text(text = "Select algorithm")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Algorithm.entries.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.name)
                    },
                    onClick = {
                        expanded = false
                        viewModel.setAlgorithm(it)
                        showVisualizer = true
                    }
                )
            }
            DropdownMenuItem(
                text = {
                    Text(text = "Dynamic Programming")
                },
                onClick = {
                    expanded = false
                    viewModel.dynamicProgramming("test", "more")
                    showDynamicProgramming = true
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Coin Change")
                },
                onClick = {
                    expanded = false
                    viewModel.runCoinChange(
                        intArrayOf(
                            1, 2, 8, 9, 4, 5, 3, 2, 1, 6, 7, 12, 10, 11, 15, 18, 20, 55, 25, 17
                        ),
                        33
                    )
                    showCoinChange = true
                }
            )
        }

        if (showVisualizer) {
            AlgorithmVisualizer(
                grid,
                algorithm.name,
                onClick = {
                    viewModel.setAlgorithm(algorithm)
                }
            )
        }

        if (showCoinChange) {
            CoinChangeVisualizer(viewModel)
        }

        if (showDynamicProgramming) {
            DpVisualizer()
        }
    }
}

@Composable
fun AlgorithmVisualizer(grid: Grid, name: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onClick() }) {
            Text("Run $name")
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            grid.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    row.forEach { cell ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = if (cell.visited) Color.Blue else Color.LightGray
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoinChangeVisualizer(viewModel: VisualizerViewModel) {
    val step by viewModel.coinSteps.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Button(onClick = {
            viewModel.runCoinChange(intArrayOf(1, 2, 5), amount = 11)
        }) {
            Text("Run Coin Change")
        }

        Spacer(Modifier.height(16.dp))

        step?.let { currentStep ->
            Text("Updating dp[${currentStep.currentIndex}] using coin ${currentStep.currentCoin}")
            Text("dp[${currentStep.sourceIndex}] = ${currentStep.valueFromSource}, so candidate = ${currentStep.valueFromSource + 1}")
            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                currentStep.dp.forEachIndexed { index, value ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                when (index) {
                                    currentStep.currentIndex -> Color.Yellow
                                    currentStep.sourceIndex -> Color.Cyan
                                    else -> Color.LightGray
                                }
                            )
                            .border(1.dp, Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (value > 1000) "∞" else value.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun DpVisualizer(viewModel: VisualizerViewModel = hiltViewModel()) {
    val dpGrid by viewModel.dpgrid.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            viewModel.dynamicProgramming("kitten", "sitting")
        }) {
            Text("Run DP")
        }

        Spacer(Modifier.height(16.dp))

        Column {
            dpGrid.forEach { row ->
                Row {
                    row.forEach { cell ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color.LightGray)
                                .border(1.dp, Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = cell.value.toString())
                        }
                    }
                }
            }
        }
    }
}
