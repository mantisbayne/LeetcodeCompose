package com.mantisbayne.leetcodecompose.data

import android.content.Context
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SolutionsService @Inject constructor(
    private val context: Context
) {

    fun getSolutions(): List<SolutionModel> {
        return getFromAssets("solutions")
    }

    fun getMatrixProblems(): List<MatrixModel> {
        return getFromAssets("matrix")
    }

    private fun <T> getFromAssets(fileName: String): List<T> {
        val json = Json { ignoreUnknownKeys = true }
        val inputStream = context.assets.open("$fileName.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val decodeFromString = json.decodeFromString<List<T>>(jsonString)
        return decodeFromString
    }
}
