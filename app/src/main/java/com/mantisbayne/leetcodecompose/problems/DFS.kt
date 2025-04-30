package com.mantisbayne.leetcodecompose.problems

fun numEnclaves(grid: Array<IntArray>): Int {
    val rowSize = grid.size
    val colSize = grid[0].size
    var trapped = 0
    val visited = Array(rowSize) { BooleanArray(colSize) { false } }

    fun isAtEdge(row: Int, col: Int) =
        row < 0 || row >= rowSize || col < 0 || col >= colSize

    fun trapped(row: Int, col: Int) = visited[row][col] || grid[row][col] == 0

    fun checkOnePath(row: Int, col: Int): Pair<Boolean, Int> {
        if (isAtEdge(row, col)) return Pair(true, 0)
        if (trapped(row, col)) return Pair(false, 0)

        visited[row][col] = true
        var touchesBoundary = false
        var count = 1

        val directions = listOf(
            Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)
        )

        for ((dx, dy) in directions) {
            val (touches, cnt) = checkOnePath(row + dx, col + dy)
            if (touches) touchesBoundary = true
            count += cnt
        }

        return Pair(touchesBoundary, count)
    }

    for (rowIndex in grid.indices) {
        for (colIndex in 0..<grid[rowIndex].size) {
            val node = grid[rowIndex][colIndex]
            if (!visited[rowIndex][colIndex] && node == 1) {
                val (valid, count) = checkOnePath(rowIndex, colIndex)
                if (!valid) {
                    // count all of the ones
                    trapped += count
                }
            }
        }
    }

    return trapped
}
