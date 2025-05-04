package com.mantisbayne.leetcodecompose.problems

fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    // backtracking - "candidate"
    val result = mutableListOf<List<Int>>()

    fun checkSum(state: MutableList<Int>, sum: Int, index: Int) {
        when {
            sum == target -> result.add(state.toList())
            sum > target -> return
            else -> {
                for (i in index..<candidates.size) {
                    // update local path
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

fun combinationSum3(k: Int, n: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    fun checkSum(candidates: MutableList<Int>, sum: Int, index: Int) {

        when {
            sum == k -> {
                result.add(candidates)
            }
            sum > k -> return
            else -> {
                for (i in index..9) {
                    candidates.add(i)
                    checkSum(candidates, sum, i)
                    candidates.removeAt(candidates.size - 1)
                }
            }
        }
    }

    checkSum(mutableListOf(), 0, 0)

    return result
}
