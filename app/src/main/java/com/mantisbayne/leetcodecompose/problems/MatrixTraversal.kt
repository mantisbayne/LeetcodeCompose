package com.mantisbayne.leetcodecompose.problems

object MatrixTraversal {

    // adjacency matrix, searching for connections, dfs
    fun findCircleNum(isConnected: Array<IntArray>): Int {
        val size = isConnected.size
        val end = size - 1
        val visitedCities = BooleanArray(size) { false }
        var connections = 0

        fun checkNeighbor(city: Int) {
            for (neighbor in 0..end) {
                if (isConnected[city][neighbor] == 1 && !visitedCities[neighbor]) {
                    visitedCities[neighbor] = true
                    checkNeighbor(neighbor)
                }
            }
        }

        for (city in 0..end) {
            if (!visitedCities[city]) {
                connections++
                visitedCities[city] = true
                checkNeighbor(city)
            }
        }

        return connections
    }

    fun setValueZeroIfColOrRowZero(matrix: List<List<Int>>): List<List<Int>> {
        if (matrix.isEmpty() || matrix[0].isEmpty()) return matrix

        val rowZeros = mutableSetOf<Int>()
        val colZeros = mutableSetOf<Int>()

        matrixLoop(matrix) { i, j, box ->
            if (box == 0) {
                rowZeros.add(i)
                colZeros.add(j)
            }
        }

        return MutableList(matrix.size) {row ->
            MutableList(matrix[0].size) {col ->
                if (row in rowZeros || col in colZeros) {
                    0
                } else {
                    matrix[row][col]
                }
            }
        }
    }
}

private fun <T> matrixLoop(matrix: List<List<T>>, action: (i: Int, j:Int, element: T) -> Unit) {

    if (matrix.isEmpty()) return
    val cols = matrix.firstOrNull()?.size ?: 0

    for (i in matrix.indices) {

        for (j in 0 until cols) {
            if (j < matrix[i].size) {
                action(i, j, matrix[i][j])
            }
        }
    }
}