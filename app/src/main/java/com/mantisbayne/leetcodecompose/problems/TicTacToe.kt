package com.mantisbayne.leetcodecompose.problems

import kotlin.math.abs

class TicTacToe(n: Int) {

    val rows = IntArray(n) { 0 }
    val columns = IntArray(n) { 0 }
    var diagonal = 0
    var antidiagonal = 0

    fun move(row: Int, col: Int, player: Int): Int {
        if (player == 1) {
            rows[row] += 1
            columns[col] += 1
        } else {
            rows[row] -= 1
            columns[col] -= 1
        }

        if (row == col) {
            if (player == 1) {
                diagonal += 1
            } else {
                diagonal -= 1
            }
        }

        if (row + col == rows.size - 1) {
            if (player == 1) {
                antidiagonal += 1
            } else {
                antidiagonal -= 1
            }
        }

        if (valueIsWin(rows[row]) || valueIsWin(columns[col]) || valueIsWin(diagonal) || valueIsWin(
                antidiagonal
            )
        ) {
            return player
        }

        return 0
    }

    private fun valueIsWin(value: Int) = abs(value) == rows.size
}
