package com.mantisbayne.leetcodecompose.problems

fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    // backtracking - "candidate"
    val result = mutableListOf<List<Int>>()

    fun checkSum(state: MutableList<Int>, sum: Int, index: Int) {
        when {
            sum == target -> result.add(state.toList())
            sum > target -> return
            else -> {
                for (i in index..candidates.size - 1) {
                    state.add(candidates[i])
                    checkSum(state, sum + candidates[i], i)
                    state.removeAt(state.size - 1)
                }
            }
        }
    }

    checkSum(mutableListOf(), 0, 0)

    return result.toList()
}
