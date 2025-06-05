package com.mantisbayne.leetcodecompose.data

import kotlinx.serialization.Serializable

@Serializable
data class SolutionModel(
    val title: String,
    val description: String,
    val category: String,
    val code: String
)

@Serializable
data class MatrixModel(
    val id: Int,
    val name: String,
    val description: String
)
