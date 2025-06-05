package com.mantisbayne.leetcodecompose.screen.algorithm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantisbayne.leetcodecompose.problems.coinChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisualizerViewModel @Inject constructor() : ViewModel() {

    private val _coinSteps = MutableStateFlow<CoinChangeStep?>(null)
    val coinSteps = _coinSteps.asStateFlow()

    private val _grid = MutableStateFlow(generateGrid(10, 10))
    val grid = _grid.asStateFlow()

    private val _dpgrid = MutableStateFlow(generateDpGrid(10, 10))
    val dpgrid = _dpgrid.asStateFlow()

    private val _algorithm = MutableStateFlow(Algorithm.EMPTY)
    val algorithm = _algorithm.asStateFlow()

    private var algorithmJob: Job? = null

    fun setAlgorithm(newAlgorithm: Algorithm) {
        algorithmJob?.cancel()
        println("debugg: cancel job $algorithmJob")
        resetGrid()
        _algorithm.value = newAlgorithm
    }

    fun processAlgorithmUpdate() {
        algorithmJob?.cancel()
        algorithmJob = viewModelScope.launch {
            println("debugg: current job $algorithmJob")
            when (_algorithm.value) {
                Algorithm.DFS -> generateDfs()
                Algorithm.BFS -> generateBfs()
                Algorithm.BACKTRACKING -> generateBacktracking()
                Algorithm.UNION_FIND -> generateUnionFind()
                Algorithm.EMPTY -> {}
            }
        }
    }

    private fun generateDfs() {
        val numRows = _grid.value.size
        val numCols = _grid.value[0].size
        val visited = Array(numRows) { BooleanArray(numCols) }

        viewModelScope.launch {
            while (isActive) {
                dfs(0, 0, visited)
            }
        }
    }

    private fun generateBfs() {
        println("debugg: generate bfs")
        val numRows = _grid.value.size
        val numCols = _grid.value[0].size
        val visited = Array(numRows) { BooleanArray(numCols) }

        viewModelScope.launch {
            while (isActive) {
                bfs(0, 0, visited)
            }
        }
    }

    fun generateUnionFind() {
        val size = 10
        val parent = IntArray(size) { it }

        viewModelScope.launch {
            for (i in 0 until 15) {
                val a = (0 until size).random()
                val b = (0 until size).random()

                val finda = find(parent, a)
                val findb = find(parent, b)

                if (finda != findb) {
                    parent[finda] = findb
                }
            }
        }
    }

    fun generateBacktracking() {
        val numRows = _grid.value.size
        val numCols = _grid.value[0].size
        val visited = Array(numRows) { BooleanArray(numCols) }

        viewModelScope.launch {
            backtracking(0, 0, visited)
        }
    }

    fun dynamicProgramming(a: String, b: String) {
        val m = _dpgrid.value.size
        val n = _dpgrid.value[0].size
        val dp = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..<m) {
            for (j in 0..<n) {
                dp[i][j] = when {
                    i == 0 -> j
                    j == 0 -> i
                    a[i - 1] == b[j - 1] -> dp[i - 1][j - 1]
                    else -> minOf(
                        dp[i - 1][j],
                        dp[i][j - 1],
                        dp[i - 1][j - 1]
                    ) + 1
                }

                updateDpCell(i, j, dp[i][j])
            }
        }

    }

    fun runCoinChange(coins: IntArray, amount: Int) {
        viewModelScope.launch {
            coinChange(coins, amount) { dp, i, coin, from, source ->
                _coinSteps.value = CoinChangeStep(
                    dp = dp,
                    currentIndex = i,
                    currentCoin = coin,
                    sourceIndex = from,
                    valueFromSource = dp[from]
                )

                delay(300)
            }
        }
    }


    private fun generateGrid(rows: Int, cols: Int): List<List<GridCell>> {
        return List(rows) { row ->
            List(cols) { col -> GridCell(row, col) }
        }
    }

    private fun generateDpGrid(rows: Int, cols: Int): List<List<DpCell>> {
        return List(rows) { row ->
            List(cols) { col -> DpCell(row, col) }
        }
    }

    private suspend fun dfs(row: Int, col: Int, visited: Array<BooleanArray>) {
        if (row !in visited.indices || col !in visited[0].indices) return
        if (visited[row][col]) return

        visited[row][col] = true
        delay(1000)
        updateGridCell(row, col)

        val directions = getDirections(row, col)

        for ((nextRow, nextCol) in directions) {
            dfs(nextRow, nextCol, visited)
        }
    }

    private fun getDirections(
        row: Int,
        col: Int
    ) = listOf(
        row to col + 1,
        row to col - 1,
        row + 1 to col,
        row - 1 to col
    )

    private suspend fun bfs(row: Int, col: Int, visited: Array<BooleanArray>) {

        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(row to col)

        while (queue.isNotEmpty()) {
            val (row, col) = queue.removeFirst()

            if (visited[row][col]) continue
            if (row !in visited.indices || col !in visited[0].indices) continue

            visited[row][col] = true
            delay(500)
            updateGridCell(row, col)

            val directions = getDirections(row, col)

            for ((nextRow, nextCol) in directions) {
                if (nextRow in visited.indices && nextCol in visited[0].indices && !visited[nextRow][nextCol]) {
                    queue.add(nextRow to nextCol)
                }
            }
        }
    }

    private suspend fun backtracking(row: Int, col: Int, visited: Array<BooleanArray>): Boolean {
        if (row !in visited.indices || col !in visited[0].indices) return false
        if (visited[row][col]) return false
        if (row == visited.lastIndex && col == visited[0].lastIndex) {
            updateGridCell(row, col)
            return true
        }

        visited[row][col] = true
        delay(1000)
        updateGridCell(row, col)

        for ((nextRow, nextCol) in getDirections(row, col)) {
            if (backtracking(nextRow, nextCol, visited)) {
                return true
            }
        }

        return false
    }

    private suspend fun find(parent: IntArray, num: Int): Int {
        if (parent[num] != num) {
            parent[num] = find(parent, parent[num])
        }
        return parent[num]
    }

    private fun updateGridCell(row: Int, col: Int) {
        _grid.value = _grid.value.map { it.toMutableList() }.also { grid ->
            println("debugg: grid $grid $row $col")
            grid[row][col] = grid[row][col].copy(visited = true)
        }
    }

    private fun updateDpCell(row: Int, col: Int, value: Int) {
        _dpgrid.value = _dpgrid.value.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                if (r == row && c == col) (cell as DpCell).copy(value = value) else cell
            }
        }
    }

    private fun resetGrid() {
        _grid.value = generateGrid(10, 10)
    }
}

enum class Algorithm {
    DFS,
    BFS,
    BACKTRACKING,
    UNION_FIND,
    EMPTY
}

data class GridCell(
    val row: Int,
    val col: Int,
    val visited: Boolean = false
)

data class DpCell(
    val row: Int,
    val col: Int,
    val value: Int = 0
)

data class CoinChangeStep(
    val dp: List<Int>,
    val currentIndex: Int,
    val currentCoin: Int,
    val sourceIndex: Int, // i - coin
    val valueFromSource: Int
)

typealias Grid = List<List<GridCell>>
