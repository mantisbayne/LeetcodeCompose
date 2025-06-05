package com.mantisbayne.leetcodecompose.screen.algorithm

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

        val grid by viewModel.grid.collectAsState()
        val algorithm by viewModel.algorithm.collectAsState()

        Button(
            onClick = {
                expanded = true
            }
        ) {
            Text(text = "Select algorithm")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Algorithm.entries
                .filter { it != Algorithm.EMPTY }.forEach {
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
        }

        if (showVisualizer) {
            AlgorithmVisualizer(
                grid,
                algorithm.name,
                onClick = {
                    viewModel.processAlgorithmUpdate()
                }
            )
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
