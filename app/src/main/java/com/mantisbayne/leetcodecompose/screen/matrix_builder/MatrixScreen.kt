package com.mantisbayne.leetcodecompose.screen.matrix_builder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.text.input.KeyboardType
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
                { Text("Matrix Transformer", modifier = Modifier.fillMaxWidth()) }
            )
        }
    ) { innerPadding ->
        // loading and error handling go here
        MatrixScreenContent(
            Modifier.padding(innerPadding),
            state,
            viewModel::handleIntent
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
    val options = (1..9).toList()

    var selectedRows by rememberSaveable { mutableStateOf<Int?>(null) }
    var rowsMenuExpanded by rememberSaveable { mutableStateOf(false) }

    var selectedCols by rememberSaveable { mutableStateOf<Int?>(null) }
    var colsMenuExpanded by rememberSaveable { mutableStateOf(false) }

    var problemMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Select Matrix Dimensions", style = MaterialTheme.typography.titleMedium)

        // Row count dropdown
        ExposedDropdownMenuBox(
            expanded = rowsMenuExpanded,
            onExpandedChange = { rowsMenuExpanded = !rowsMenuExpanded }
        ) {
            TextField(
                value = selectedRows?.toString() ?: "Select rows (N)",
                onValueChange = {},
                readOnly = true,
                label = { Text("Rows (N)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = rowsMenuExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = rowsMenuExpanded,
                onDismissRequest = { rowsMenuExpanded = false }
            ) {
                options.forEach { row ->
                    DropdownMenuItem(
                        text = { Text(row.toString()) },
                        onClick = {
                            selectedRows = row
                            rowsMenuExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = colsMenuExpanded,
            onExpandedChange = { colsMenuExpanded = !colsMenuExpanded }
        ) {
            TextField(
                value = selectedCols?.toString() ?: "Select columns (M)",
                onValueChange = {},
                readOnly = true,
                label = { Text("Columns (M)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = colsMenuExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = colsMenuExpanded,
                onDismissRequest = { colsMenuExpanded = false }
            ) {
                options.forEach { col ->
                    DropdownMenuItem(
                        text = { Text(col.toString()) },
                        onClick = {
                            selectedCols = col
                            colsMenuExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = problemMenuExpanded,
            onExpandedChange = { problemMenuExpanded = !problemMenuExpanded }
        ) {
            TextField(
                value = state.currentSelectedProblem.name.ifBlank { "Select a problem" },
                onValueChange = {},
                readOnly = true,
                label = { Text("Choose problem") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = problemMenuExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = problemMenuExpanded,
                onDismissRequest = { problemMenuExpanded = false }
            ) {
                state.menuItems.forEach { problemItem ->
                    DropdownMenuItem(
                        text = { Text(problemItem.name) },
                        onClick = {
                            onIntent(MatrixEvent.UpdateCurrentProblem(problemItem))
                            problemMenuExpanded = false
                        }
                    )
                }
            }
        }

        AnimatedVisibility(state.problemDescriptionVisibility) {
            Text(state.currentSelectedProblem.description)
        }

        Button(
            enabled = selectedRows != null && selectedCols != null,
            onClick = {
                onIntent(
                    MatrixEvent.BuildMatrix(
                        selectedRows!!,
                        selectedCols!!,
                        state.currentSelectedProblem
                    )
                )
            }
        ) {
            Text("Build Matrix")
        }
    }
}
