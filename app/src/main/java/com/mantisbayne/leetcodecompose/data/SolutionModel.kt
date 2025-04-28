package com.mantisbayne.leetcodecompose.data

import kotlinx.serialization.Serializable

@Serializable
data class SolutionModel(
    val title: String,
    val description: String,
    val category: String,
    val code: String
)
