package com.mantisbayne.leetcodecompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mantisbayne.leetcodecompose.data.SolutionModel
import com.mantisbayne.leetcodecompose.viewmodel.SolutionViewModel
import com.mantisbayne.navigation.NavHostRoutes

@Composable
fun SolutionScreen(
    modifier: Modifier = Modifier,
    viewModel: SolutionViewModel,
    navController: NavController
) {
    val uiState by viewModel.solutions.collectAsStateWithLifecycle()
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var expandedSolution by rememberSaveable { mutableStateOf<String?>(null) }

    when {
        uiState.loading -> LoadingState(modifier)
        uiState.empty -> Text("No solutions available.")
        !uiState.error.isNullOrBlank() -> ErrorState(modifier, uiState.error ?: "An error occurred")
        else -> SolutionsContent(
            modifier,
            uiState.solutions,
            {
                selectedCategory = it
                expandedSolution = null
            },
            { solution ->
                val expanded = solution.title == expandedSolution
                expandedSolution = if (expanded) null else solution.title
            },
            selectedCategory,
            expandedSolution,
            navController
        )
    }
}

@Composable
fun LoadingState(modifier: Modifier) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(modifier: Modifier, text: String) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "error",
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun SolutionsContent(
    modifier: Modifier,
    solutions: List<SolutionModel>,
    onCategoryClicked: (String?) -> Unit,
    onItemClicked: (SolutionModel) -> Unit,
    selectedCategory: String?,
    expandedSolution: String?,
    navController: NavController
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate(NavHostRoutes.VISUALIZER.name) }
                ) {
                    Text(text = "Algorithm Visualizer")
                }
                CategoryDropDown(
                    solutions.map { it.category }.distinct(),
                    selectedCategory = selectedCategory,
                    onCategoryClicked = {
                        onCategoryClicked(it)
                    }
                )
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(solutions.filter { selectedCategory == null || it.category == selectedCategory }) { solution ->
                val expanded = solution.title == expandedSolution
                SolutionItem(
                    expanded,
                    solution,
                    onItemClicked = {
                        onItemClicked(solution)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryDropDown(
    categories: List<String>,
    selectedCategory: String?,
    onCategoryClicked: (String?) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Button(onClick = { expanded = true }) {
            Text(selectedCategory ?: "Select category")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = {
                    Text("All")
                },
                onClick = {
                    onCategoryClicked(null)
                    expanded = false
                }
            )
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(category)
                    },
                    onClick = {
                        onCategoryClicked(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SolutionItem(
    isExpanded: Boolean,
    solutionModel: SolutionModel,
    onItemClicked: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = solutionModel.title, style = MaterialTheme.typography.titleMedium)
            Text(text = solutionModel.description)
            if (isExpanded) {
                Spacer(Modifier.height(8.dp))
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    Text(
                        text = solutionModel.code,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
