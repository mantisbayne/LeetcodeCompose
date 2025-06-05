package com.mantisbayne.leetcodecompose.screen.matrix_builder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<MatrixViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                { Text("Matrix Transformer") }
            )
        }
    ) { innerPadding ->
        // loading and error handling go here
        MatrixScreenContent(
            Modifier.padding(innerPadding),
            state
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixScreenContent(
    modifier: Modifier,
    state: MatrixUiState,
    onIntent: (MatrixEvent) -> Unit
) {
    var rowInput by rememberSaveable { mutableStateOf("") }
    var colInput by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Enter Matrix Dimensions",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            modifier = Modifier.height(56.dp),
            value = rowInput,
            onValueChange = {
                rowInput = it
            },
            label = {
                Text("N (rows)", style = MaterialTheme.typography.bodyLarge)
            },
            placeholder = {
                Text("Enter number of rows")
            },
            textStyle = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.height(56.dp),
            value = colInput,
            onValueChange = {
                colInput = it
                // update cols in state
            },
            label = {
                Text("M (columns)", style = MaterialTheme.typography.bodyLarge)
            },
            placeholder = {
                Text("Enter number of columns")
            },
            textStyle = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            ExposedDropdownMenu(
                modifier = Modifier.padding(16.dp),
                expanded = expanded,
                onDismissRequest = {
                    expanded = !expanded
                }
            ) {
                state.menuItems.forEach { problemItem ->
                    DropdownMenuItem(
                        text = { Text(problemItem) },
                        onClick = {
                            onIntent(
                                MatrixEvent.BuildMatrix(
                                    rowInput.toInt(),
                                    colInput.toInt(),
                                    problemItem
                                )
                            )
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.problemDescriptionVisibility
        ) {
            Text(
                state.currentSelectedProblem.name
            )
        }

        Button(
            onClick = {
                onIntent(
                    MatrixEvent.BuildMatrix(
                        rowInput.toInt(),
                        colInput.toInt(),
                        problemItem
                    )
                )
            }
        ) { }
    }
}
