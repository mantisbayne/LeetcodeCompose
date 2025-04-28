package com.mantisbayne.leetcodecompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantisbayne.leetcodecompose.App
import com.mantisbayne.leetcodecompose.data.SolutionModel
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SolutionViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _solutions = MutableStateFlow<List<SolutionModel>>(emptyList())
    val solutions: StateFlow<List<SolutionModel>> = _solutions.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    init {
        loadSolutionsFromAssets()
    }

    private fun loadSolutionsFromAssets() {
        viewModelScope.launch {
            try {
                val inputStream = application.assets.open("solutions.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val loadedSolutions = json.decodeFromString<List<SolutionModel>>(jsonString)
                _solutions.update { loadedSolutions }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
