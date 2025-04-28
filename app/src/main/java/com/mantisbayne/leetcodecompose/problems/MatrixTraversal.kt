package com.mantisbayne.leetcodecompose.problems

class MatrixTraversal {

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
}