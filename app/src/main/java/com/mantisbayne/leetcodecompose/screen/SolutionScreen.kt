package com.mantisbayne.leetcodecompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.mantisbayne.leetcodecompose.data.SolutionModel
import com.mantisbayne.leetcodecompose.viewmodel.SolutionViewModel

@Composable
fun SolutionScreen(
    modifier: Modifier,
    viewModel: SolutionViewModel
) {
    val solutions by viewModel.solutions.collectAsState()
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var expandedSolution by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CategoryDropDown(
            solutions.map { it.category }.distinct(),
            selectedCategory = selectedCategory,
            onCategoryClicked = {
                selectedCategory = it
                expandedSolution = null
            }
        )

        if (solutions.isEmpty()) {
            Text("No solutions available.")
        } else {
            LazyColumn(
                Modifier.fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(solutions.filter { selectedCategory == null || it.category == selectedCategory }) { solution ->
                    val expanded = solution.title == expandedSolution
                    SolutionItem(
                        expanded,
                        solution,
                        onItemClicked = {
                            expandedSolution = if (expanded) null else solution.title
                        }
                    )
                }
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
        modifier = Modifier.fillMaxWidth(),
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
            Modifier.fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = solutionModel.title, style = MaterialTheme.typography.titleMedium)
            Text(text = solutionModel.description)
            if (isExpanded) {
                Spacer(Modifier.height(8.dp))
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimary)
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
