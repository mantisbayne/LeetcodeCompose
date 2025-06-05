package com.mantisbayne.leetcodecompose.data

import android.content.Context
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SolutionsService @Inject constructor(
    private val context: Context
) {

    fun getSolutions(): List<SolutionModel> {
        val json = Json { ignoreUnknownKeys = true }
        val inputStream = context.assets.open("solutions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val decodeFromString = json.decodeFromString<List<SolutionModel>>(jsonString)
        return decodeFromString
    }
}
