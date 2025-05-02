package com.mantisbayne.leetcodecompose.problems

import com.mantisbayne.leetcodecompose.viewmodel.CoinChangeStep
import kotlinx.coroutines.delay

fun climbStairs(n: Int): Int {
    if (n <= 2) return n
    val memo = IntArray(n + 1)
    memo[1] = 1
    memo[2] = 2

    for (i in 3..n) {
        memo[i] = memo[i - 1] + memo[i - 2]
    }

    return memo[n]
}

//Input: nums = [10,9,2,5,3,7,101,18]
//Output: 4
fun lengthOfLIS(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    // store the list at every index
    val allLis = IntArray(nums.size) { 1 }

    for (i in nums.indices) {
        // every index before it
        for (j in 0 until i) {
            // if the number is smaller
            if (nums[i] > nums[j]) {
                // the longest path at that index is the current path or the max of every previous path
                allLis[i] = 1 + maxOf(allLis[i], allLis[j] + 1)
            }
        }
    }

    return allLis.maxOrNull() ?: 0
}

suspend fun coinChange(
    coins: IntArray,
    amount: Int,
    onCoinChange: suspend (dp: List<Int>, currentIndex: Int, currentCoin: Int, sourceIndex: Int, valueFromSource: Int) -> Unit
) {
    // save the lengths, make the size larger than the amount
    val dp = IntArray(amount + 1) { amount + 1 }
    // looking at all numbers from 0 to amount.  what is amount 0?
    dp[0] = 0

    // loop over
    for (i in 1..amount) {
        for (coin in coins) {
            val from = i - coin
            if (from >= 0) {
                val candidate = dp[from] + 1
                if (candidate < dp[i]) {
                    dp[i] = candidate
                }

                onCoinChange(dp.toList(), i, coin, from, dp[from])
            }
        }
    }
}